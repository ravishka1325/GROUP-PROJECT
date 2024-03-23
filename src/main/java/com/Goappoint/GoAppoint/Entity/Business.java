package com.Goappoint.GoAppoint.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int businessId;
    private String businessName;
    private String ownerName;
    private String businessEmail;
    private String businessIndustryType;
    private String businessAddress;
    private String businessCity;
    private String businessDistrict;
    private String businessZip;
    private String businessMobile;
    private String businessPassword;
    private String businessAbout;

    private String mainBannerImagePath;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Service> services;

    @ElementCollection
    @CollectionTable(name = "business_hours", joinColumns = @JoinColumn(name = "business_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "hours")
    private Map<String, String> businessHours;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}