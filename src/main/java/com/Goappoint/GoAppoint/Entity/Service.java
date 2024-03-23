package com.Goappoint.GoAppoint.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;
    private String serviceName;
    private double servicePrice;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    @JsonIgnoreProperties({"services", "appointments"})
    private Business business;

}
