package com.Goappoint.GoAppoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BpageDTO {
    private int businessId;
    private String businessName;
    private String ownerName;
    private String businessAddress;
    private String businessAbout;
    private String businessMobile;
}
