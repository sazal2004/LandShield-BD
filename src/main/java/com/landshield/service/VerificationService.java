package com.landshield.service;

import com.landshield.model.*;
import com.landshield.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerificationService {

    private final PropertyRepository propertyRepository;
    private final BaseOwnershipRepository ownershipRepository;
    private final TransferRepository transferRepository;
    private final DeedRepository deedRepository;
    private final MutationRepository mutationRepository;
    private final VerificationCaseRepository caseRepository;

    public VerificationService(
            PropertyRepository propertyRepository,
            BaseOwnershipRepository ownershipRepository,
            TransferRepository transferRepository,
            DeedRepository deedRepository,
            MutationRepository mutationRepository,
            VerificationCaseRepository caseRepository) {
        this.propertyRepository = propertyRepository;
        this.ownershipRepository = ownershipRepository;
        this.transferRepository = transferRepository;
        this.deedRepository = deedRepository;
        this.mutationRepository = mutationRepository;
        this.caseRepository = caseRepository;
    }

    /** Base ownership + completed incoming - completed outgoing. */
    public double calculateCurrentOwnership(Long propertyId, String personName) {
        double base = ownershipRepository
                .findByPropertyIdAndOwnerNameIgnoreCase(propertyId, personName)
                .map(o -> o.baseArea)
                .orElse(0.0);

        List<TransferRecord> transfers = transferRepository.findByPropertyId(propertyId);

        double incoming = transfers.stream()
                .filter(t -> "COMPLETED".equalsIgnoreCase(t.status)
                        && personName.equalsIgnoreCase(t.buyerName))
                .mapToDouble(t -> t.transferredArea)
                .sum();

        double outgoing = transfers.stream()
                .filter(t -> "COMPLETED".equalsIgnoreCase(t.status)
                        && personName.equalsIgnoreCase(t.sellerName))
                .mapToDouble(t -> t.transferredArea)
                .sum();

        return base + incoming - outgoing;
    }

    public VerificationResult verify(
            String district, String upazila, String mouza,
            String khatianNo, String plotNo,
            String sellerName, String buyerName,
            String shareRef, double proposedArea) {

        district = district.trim();
        upazila = upazila.trim();
        mouza = mouza.trim();
        khatianNo = khatianNo.trim();
        plotNo = plotNo.trim();
        sellerName = sellerName.trim();
        buyerName = buyerName.trim();
        shareRef = shareRef.trim();

        if (proposedArea <= 0) {
            throw new RuntimeException("প্রস্তাবিত হস্তান্তরের জমির পরিমাণ শূন্যের চেয়ে বেশি হতে হবে।");
        }
        if (sellerName.equalsIgnoreCase(buyerName)) {
            throw new RuntimeException("বিক্রেতা ও ক্রেতা একই ব্যক্তি হতে পারবেন না।");
        }

        PropertyRecord property = propertyRepository
                .findByKhatianNoIgnoreCaseAndPlotNoIgnoreCase(khatianNo, plotNo)
                .orElseThrow(() -> new RuntimeException("এই খতিয়ান ও দাগ নম্বরের কোনো সম্পত্তির রেকর্ড পাওয়া যায়নি।"));

        final String seller = sellerName;
        final String share = shareRef;

        VerificationResult result = new VerificationResult();
        result.propertyId = property.id;
        result.district = property.district;
        result.upazila = property.upazila;
        result.mouza = property.mouza;
        result.khatianNo = property.khatianNo;
        result.plotNo = property.plotNo;
        result.sellerName = sellerName;
        result.buyerName = buyerName;
        result.shareRef = shareRef;
        result.proposedArea = proposedArea;

        result.propertyDetailsMatched =
                property.district.equalsIgnoreCase(district)
                        && property.upazila.equalsIgnoreCase(upazila)
                        && property.mouza.equalsIgnoreCase(mouza);

        result.currentSellerBalance = calculateCurrentOwnership(property.id, sellerName);
        result.currentBuyerBalance = calculateCurrentOwnership(property.id, buyerName);
        result.sellerMatched = result.currentSellerBalance > 0.0001;

        List<TransferRecord> transfers = transferRepository.findByPropertyId(property.id);
        result.history = transfers;

        // DOUBLE SELLING
        result.doubleSelling = transfers.stream().anyMatch(t ->
                seller.equalsIgnoreCase(t.sellerName)
                        && share.equalsIgnoreCase(t.shareRef)
                        && "COMPLETED".equalsIgnoreCase(t.status));

        // PENDING / OVERLAPPING TRANSACTION
        result.transactionConflict = transfers.stream().anyMatch(t ->
                seller.equalsIgnoreCase(t.sellerName)
                        && share.equalsIgnoreCase(t.shareRef)
                        && "PENDING".equalsIgnoreCase(t.status));

        // OVER SELLING
        result.overSelling = proposedArea > result.currentSellerBalance + 0.0001;
        if (result.overSelling) {
            result.excessArea = proposedArea - result.currentSellerBalance;
        }

        // DEED CHECK
        Optional<DeedRecord> deedOpt = deedRepository.findByPropertyId(property.id);
        Optional<BaseOwnership> baseOpt =
                ownershipRepository.findByPropertyIdAndOwnerNameIgnoreCase(property.id, sellerName);

        if (deedOpt.isEmpty()) {
            result.deedMismatch = true;
        } else {
            DeedRecord deed = deedOpt.get();
            boolean ownerMismatch = !deed.ownerName.equalsIgnoreCase(sellerName);
            boolean khatianMismatch = !deed.khatianNo.equalsIgnoreCase(property.khatianNo);
            boolean plotMismatch = !deed.plotNo.equalsIgnoreCase(property.plotNo);
            boolean areaMismatch = baseOpt.isPresent()
                    && Math.abs(deed.landArea - baseOpt.get().baseArea) > 0.001;
            result.deedMismatch = ownerMismatch || khatianMismatch || plotMismatch || areaMismatch;
        }

        // MUTATION CHECK
        Optional<MutationRecord> mutationOpt = mutationRepository.findByPropertyId(property.id);
        if (mutationOpt.isEmpty()) {
            result.mutationMismatch = true;
        } else {
            MutationRecord mutation = mutationOpt.get();
            boolean ownerMismatch = !mutation.ownerName.equalsIgnoreCase(sellerName);
            boolean khatianMismatch = !mutation.khatianNo.equalsIgnoreCase(property.khatianNo);
            boolean plotMismatch = !mutation.plotNo.equalsIgnoreCase(property.plotNo);
            boolean areaMismatch = deedOpt.isPresent()
                    && Math.abs(mutation.landArea - deedOpt.get().landArea) > 0.001;
            result.mutationMismatch = ownerMismatch || khatianMismatch || plotMismatch || areaMismatch;
        }

        // RISK REASONS + SCORE
        int score = 0;
        if (!result.propertyDetailsMatched) {
            score += 20;
            result.reasons.add("প্রদত্ত অবস্থানের তথ্য (জেলা/উপজেলা/মৌজা) সম্পত্তির রেকর্ডের সাথে পুরোপুরি মেলেনি।");
        }
        if (!result.sellerMatched) {
            score += 60;
            result.reasons.add("এই সম্পত্তিতে বিক্রেতার বর্তমানে হস্তান্তরযোগ্য কোনো মালিকানা ব্যালেন্স নেই।");
        }
        if (result.deedMismatch) {
            score += 20;
            result.reasons.add("দলিলের তথ্য মালিকানা/সম্পত্তির রেকর্ডের সাথে অসামঞ্জস্যপূর্ণ।");
        }
        if (result.mutationMismatch) {
            score += 20;
            result.reasons.add("নামজারি (মিউটেশন) তথ্য দলিল/সম্পত্তির রেকর্ডের সাথে অসামঞ্জস্যপূর্ণ।");
        }
        if (result.overSelling) {
            score += 60;
            result.reasons.add("প্রস্তাবিত হস্তান্তর বিক্রেতার বর্তমান হস্তান্তরযোগ্য মালিকানার চেয়ে "
                    + String.format("%.2f", result.excessArea) + " ডেসিমাল বেশি।");
        }
        if (result.doubleSelling) {
            score += 60;
            result.reasons.add("একই ডেমো ভূমি-অংশ রেফারেন্স আগেই একটি সম্পন্ন হস্তান্তরে ব্যবহৃত হয়েছে, যা সম্ভাব্য দ্বৈত-বিক্রয়ের (ডাবল সেলিং) ইঙ্গিত দেয়।");
        }
        if (result.transactionConflict) {
            score += 50;
            result.reasons.add("একই ডেমো ভূমি-অংশ রেফারেন্সে ইতোমধ্যে একটি চলমান/অপেক্ষমাণ লেনদেন রয়েছে।");
        }

        result.riskScore = Math.min(score, 100);
        if (result.riskScore >= 50) {
            result.riskLevel = "HIGH";
        } else if (result.riskScore >= 20) {
            result.riskLevel = "MEDIUM";
        } else {
            result.riskLevel = "LOW";
        }

        if (result.reasons.isEmpty()) {
            result.reasons.add("প্রোটোটাইপের নিয়ম অনুযায়ী কোনো বড় ঝুঁকির লক্ষণ পাওয়া যায়নি।");
        }

        result.canComplete = "LOW".equals(result.riskLevel);

        // SAVE VERIFICATION CASE FOR OFFICER
        String caseRef = "LS-" + System.currentTimeMillis();
        result.caseRef = caseRef;

        VerificationCase vc = new VerificationCase();
        vc.caseRef = caseRef;
        vc.propertyId = property.id;
        vc.sellerName = sellerName;
        vc.buyerName = buyerName;
        vc.proposedArea = proposedArea;
        vc.shareRef = shareRef;
        vc.riskScore = result.riskScore;
        vc.riskLevel = result.riskLevel;
        String joined = String.join(" | ", result.reasons);
        vc.reasons = joined.length() > 2000 ? joined.substring(0, 2000) : joined;
        vc.createdAt = LocalDateTime.now().toString();
        vc.reviewStatus = "LOW".equals(result.riskLevel) ? "NO_REVIEW_REQUIRED" : "PENDING_REVIEW";
        caseRepository.save(vc);

        return result;
    }
}
