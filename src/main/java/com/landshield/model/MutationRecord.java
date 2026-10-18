package com.landshield.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mutation_records")
public class MutationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "property_id")
    public Long propertyId;
    @Column(name = "mutation_no")
    public String mutationNo;
    @Column(name = "owner_name")
    public String ownerName;
    @Column(name = "khatian_no")
    public String khatianNo;
    @Column(name = "plot_no")
    public String plotNo;
    @Column(name = "land_area")
    public double landArea;
}
