package com.Goappoint.GoAppoint.Controller;

import com.Goappoint.GoAppoint.DTO.ResponseDTO;
import com.Goappoint.GoAppoint.DTO.UserDTO;
import com.Goappoint.GoAppoint.Entity.User;
import com.Goappoint.GoAppoint.Service.UserService;
import com.Goappoint.GoAppoint.util.VarList;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/saveUser")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO){
        try{
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            String res = userService.saveUser(userDTO);
            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("06")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMesseage("User Registered");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMesseage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        try{
            String res = userService.updateUser(userDTO);
            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("01")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMesseage("Not A Registered User");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMesseage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDTO, HttpSession session) {
        try {
            User user = userService.authenticate(userDTO.getUserEmail(), userDTO.getPassword());
            if (user != null) {
                if (session.isNew()) {
                    // Session has just started
                    session.setAttribute("user", user);
                    responseDTO.setCode(VarList.RSP_SUCCESS);
                    responseDTO.setMesseage("Login successful");
                    responseDTO.setContent(user);
                    return ResponseEntity.ok(responseDTO);
                } else {
                    // Session was already started
                    responseDTO.setCode(VarList.RSP_SUCCESS);
                    responseDTO.setMesseage("Login successful");
                    responseDTO.setContent(user);
                    return ResponseEntity.ok(responseDTO);
                }
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMesseage("Invalid email or password");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ResponseDTO> logout(HttpSession session) {
        try {
            session.invalidate(); // This invalidates the user's session on the server-side

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMesseage("Logout successful");
            responseDTO.setContent(null);

            return ResponseEntity.ok(responseDTO);

        } catch (Exception ex) {

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

}
