package com.landshield.model;

import jakarta.persistence.*;

@Entity
@Table(name = "verification_cases")
public class VerificationCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "case_ref")
    public String caseRef;
    @Column(name = "property_id")
    public Long propertyId;
    @Column(name = "seller_name")
    public String sellerName;
    @Column(name = "buyer_name")
    public String buyerName;
    @Column(name = "proposed_area")
    public double proposedArea;
    @Column(name = "share_ref")
    public String shareRef;
    @Column(name = "risk_score")
    public int riskScore;
    @Column(name = "risk_level")
    public String riskLevel;
    @Column(length = 2000)
    public String reasons;
    @Column(name = "created_at")
    public String createdAt;
    @Column(name = "review_status")
    public String reviewStatus;
}
