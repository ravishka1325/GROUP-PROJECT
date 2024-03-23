package com.Goappoint.GoAppoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessHoursDTO {
    private int businessId;
    private Map<String, String> businessHours;
}