package com.Goappoint.GoAppoint.DTO;

import com.Goappoint.GoAppoint.Entity.Business;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessDTO {
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

    public static BusinessDTO fromEntity(Business business) {
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setBusinessId(business.getBusinessId());
        businessDTO.setBusinessName(business.getBusinessName());
        businessDTO.setOwnerName(business.getOwnerName());
        businessDTO.setBusinessEmail(business.getBusinessEmail());
        businessDTO.setBusinessIndustryType(business.getBusinessIndustryType());
        businessDTO.setBusinessAddress(business.getBusinessAddress());
        businessDTO.setBusinessCity(business.getBusinessCity());
        businessDTO.setBusinessDistrict(business.getBusinessDistrict());
        businessDTO.setBusinessZip(business.getBusinessZip());
        businessDTO.setBusinessMobile(business.getBusinessMobile());
        businessDTO.setBusinessPassword(business.getBusinessPassword());
        return businessDTO;
    }
}
