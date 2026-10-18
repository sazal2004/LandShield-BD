package com.landshield.model;

import jakarta.persistence.*;

@Entity
@Table(name = "base_ownerships")
public class BaseOwnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "property_id")
    public Long propertyId;
    @Column(name = "owner_name")
    public String ownerName;
    @Column(name = "base_area")
    public double baseArea;
}
