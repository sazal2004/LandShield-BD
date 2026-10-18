package com.landshield.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transfer_records")
public class TransferRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "property_id")
    public Long propertyId;
    @Column(name = "seller_name")
    public String sellerName;
    @Column(name = "buyer_name")
    public String buyerName;
    @Column(name = "transferred_area")
    public double transferredArea;
    @Column(name = "share_ref")
    public String shareRef;
    @Column(name = "transaction_date")
    public String transactionDate;
    public String status;
    @Column(name = "demo_generated")
    public boolean demoGenerated;
}
