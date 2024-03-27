package com.Goappoint.GoAppoint.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.Goappoint.GoAppoint.DTO.UserDTO;
import com.Goappoint.GoAppoint.Entity.User;
import com.Goappoint.GoAppoint.Repo.UserRepo;
import com.Goappoint.GoAppoint.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public String saveUser(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserID())){
            return VarList.RSP_DUPLICATED;
        }else{
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.RSP_SUCCESS;
        }
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String userEmail, String password) {
        User user = userRepo.findByUserEmail(userEmail);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


}
