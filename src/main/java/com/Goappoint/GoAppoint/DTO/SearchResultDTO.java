package com.Goappoint.GoAppoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDTO {
    private int businessId;
    private String businessName;
    private String businessAddress;
    private String businessCity;
    private String businessMobile;
    private String businessIndustryType;
    private String mainBannerImagePath;
}