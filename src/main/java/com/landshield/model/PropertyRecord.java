package com.landshield.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_records")
public class PropertyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String district;
    public String upazila;
    public String mouza;
    @Column(name = "khatian_no")
    public String khatianNo;
    @Column(name = "plot_no")
    public String plotNo;
    @Column(name = "total_area")
    public double totalArea;
}
