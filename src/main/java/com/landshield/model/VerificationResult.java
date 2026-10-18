package com.landshield.model;

import java.util.ArrayList;
import java.util.List;

public class VerificationResult {
    public String caseRef;
    public Long propertyId;
    public String district;
    public String upazila;
    public String mouza;
    public String khatianNo;
    public String plotNo;
    public String sellerName;
    public String buyerName;
    public String shareRef;
    public double proposedArea;
    public boolean propertyDetailsMatched;
    public boolean sellerMatched;
    public boolean deedMismatch;
    public boolean mutationMismatch;
    public boolean overSelling;
    public boolean doubleSelling;
    public boolean transactionConflict;
    public double currentSellerBalance;
    public double currentBuyerBalance;
    public double excessArea;
    public int riskScore;
    public String riskLevel;
    public boolean canComplete;
    public List<String> reasons = new ArrayList<>();
    public List<TransferRecord> history = new ArrayList<>();
}
