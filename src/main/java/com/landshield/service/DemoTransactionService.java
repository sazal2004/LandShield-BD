package com.landshield.service;

import com.landshield.model.CompletionResult;
import com.landshield.model.TransferRecord;
import com.landshield.repository.TransferRepository;
import com.landshield.repository.VerificationCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemoTransactionService {

    private final TransferRepository transferRepository;
    private final VerificationService verificationService;
    private final VerificationCaseRepository caseRepository;

    public DemoTransactionService(TransferRepository transferRepository,
                                  VerificationService verificationService,
                                  VerificationCaseRepository caseRepository) {
        this.transferRepository = transferRepository;
        this.verificationService = verificationService;
        this.caseRepository = caseRepository;
    }

    @Transactional
    public CompletionResult completeTransaction(
            Long propertyId, String sellerName, String buyerName,
            double area, String shareRef) {

        if (area <= 0) {
            throw new RuntimeException("হস্তান্তরের জমির পরিমাণ শূন্যের চেয়ে বেশি হতে হবে।");
        }
        if (sellerName.equalsIgnoreCase(buyerName)) {
            throw new RuntimeException("বিক্রেতা ও ক্রেতা একই ব্যক্তি হতে পারবেন না।");
        }

        double sellerOld = verificationService.calculateCurrentOwnership(propertyId, sellerName);
        double buyerOld = verificationService.calculateCurrentOwnership(propertyId, buyerName);

        if (area > sellerOld + 0.0001) {
            throw new RuntimeException(
                    "লেনদেন সম্পন্ন করা যাবে না, কারণ বিক্রেতার পর্যাপ্ত হস্তান্তরযোগ্য মালিকানা নেই।");
        }

        List<TransferRecord> existing = transferRepository.findByPropertyId(propertyId);
        boolean conflict = existing.stream().anyMatch(t ->
                sellerName.equalsIgnoreCase(t.sellerName)
                        && shareRef.equalsIgnoreCase(t.shareRef));
        if (conflict) {
            throw new RuntimeException(
                    "লেনদেন সম্পন্ন করা যাবে না, কারণ এই ভূমি-অংশ রেফারেন্সে আগেই একটি লেনদেনের রেকর্ড আছে।");
        }

        TransferRecord transfer = new TransferRecord();
        transfer.propertyId = propertyId;
        transfer.sellerName = sellerName;
        transfer.buyerName = buyerName;
        transfer.transferredArea = area;
        transfer.shareRef = shareRef;
        transfer.transactionDate = LocalDate.now().toString();
        transfer.status = "COMPLETED";
        transfer.demoGenerated = true;
        transferRepository.save(transfer);

        CompletionResult result = new CompletionResult();
        result.sellerName = sellerName;
        result.buyerName = buyerName;
        result.transferredArea = area;
        result.sellerOldBalance = sellerOld;
        result.buyerOldBalance = buyerOld;
        result.sellerNewBalance = verificationService.calculateCurrentOwnership(propertyId, sellerName);
        result.buyerNewBalance = verificationService.calculateCurrentOwnership(propertyId, buyerName);
        result.shareRef = shareRef;
        return result;
    }

    /**
     * Deletes every runtime demo transfer (demo_generated = TRUE) and the verification cases created
     * while testing. Seed data (demo_generated = FALSE) is untouched, so balances return to the
     * original values (e.g. KH-103 / Karim Hasan back to 10).
     * Runs in ONE explicit transaction so the delete is committed before the redirect.
     *
     * @return number of demo transfers removed
     */
    @Transactional
    public int resetRuntimeDemoTransactions() {
        int removed = transferRepository.deleteAllDemoGenerated();
        caseRepository.deleteAllInBatch();
        return removed;
    }
}
