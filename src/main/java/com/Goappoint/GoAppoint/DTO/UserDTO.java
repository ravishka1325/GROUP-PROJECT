package com.Goappoint.GoAppoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int userID;
    private String userFname;
    private String userLname;
    private String userEmail;
    private String userMobile;
    private String userAddress;
    private String city;
    private String district;
    private String zip;
    private String password;
}
