package com.Goappoint.GoAppoint.DTO;

import com.Goappoint.GoAppoint.Entity.User;
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

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(user.getUserID());
        userDTO.setUserFname(user.getUserFname());
        userDTO.setUserLname(user.getUserLname());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setUserMobile(user.getUserMobile());
        userDTO.setUserAddress(user.getUserAddress());
        userDTO.setCity(user.getCity());
        userDTO.setDistrict(user.getDistrict());
        userDTO.setZip(user.getZip());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
