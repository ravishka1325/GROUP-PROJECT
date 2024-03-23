package com.Goappoint.GoAppoint.Controller;

import com.Goappoint.GoAppoint.DTO.*;
import com.Goappoint.GoAppoint.Entity.Business;
import com.Goappoint.GoAppoint.Service.BusinessService;
import com.Goappoint.GoAppoint.util.VarList;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/business")
@CrossOrigin
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/saveBusiness")
    public ResponseEntity saveBusiness(@RequestBody BusinessDTO businessDTO){
        try{
            businessDTO.setBusinessPassword(passwordEncoder.encode(businessDTO.getBusinessPassword()));
            String res = businessService.saveBusiness(businessDTO);
            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(businessDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("06")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMesseage("User Registered");
                responseDTO.setContent(businessDTO);
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
    public ResponseEntity<ResponseDTO> businessLogin(@RequestBody BusinessDTO businessDTO, HttpSession session) {
        try {
            Business business = businessService.authenticate(businessDTO.getBusinessEmail(), businessDTO.getBusinessPassword());
            if (business != null) {
                if (session.isNew()) {
                    // Session has just started
                    session.setAttribute("business", business);
                    responseDTO.setCode(VarList.RSP_SUCCESS);
                    responseDTO.setMesseage("Login successful");
                    responseDTO.setContent(BusinessDTO.fromEntity(business));
                    return ResponseEntity.ok(responseDTO);
                } else {
                    // Session was already started
                    responseDTO.setCode(VarList.RSP_SUCCESS);
                    responseDTO.setMesseage("Login successful");
                    responseDTO.setContent(BusinessDTO.fromEntity(business));
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

    @PutMapping(value = "/updateBusiness")
    public ResponseEntity updateBusiness(@RequestBody BpageDTO bpageDTO){
        try{
            String res = businessService.updateBusiness(bpageDTO);
            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(bpageDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("01")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMesseage("Not A Registered User");
                responseDTO.setContent(bpageDTO);
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

    @GetMapping("/getBusiness/{businessId}")
    public ResponseEntity<ResponseDTO> getBusiness(@PathVariable int businessId) {
        try {
            Business business = businessService.findById(businessId);
            if (business != null) {
                BpageDTO bpageDTO = modelMapper.map(business, BpageDTO.class);
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(bpageDTO);
                return ResponseEntity.ok(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMesseage("Business not found");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PutMapping("/updateBusinessHours")
    public ResponseEntity<ResponseDTO> updateBusinessHours(@RequestBody BusinessHoursDTO businessHoursDTO) {
        try {
            String res = businessService.updateBusinessHours(businessHoursDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Business hours updated successfully");
                responseDTO.setContent(businessHoursDTO);
                return ResponseEntity.ok(responseDTO);
            } else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMesseage("Business not found");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMesseage("Error updating business hours");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @GetMapping("/getBusinessHours/{businessId}")
    public ResponseEntity<ResponseDTO> getBusinessHours(@PathVariable int businessId) {
        try {
            Business business = businessService.findById(businessId);
            if (business != null) {
                Map<String, String> businessHours = business.getBusinessHours();
                BusinessHoursDTO businessHoursDTO = new BusinessHoursDTO(businessId, businessHours);
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(businessHoursDTO);
                return ResponseEntity.ok(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMesseage("Business not found");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping("/uploadBusinessImage")
    public ResponseEntity<ResponseDTO> uploadBusinessImage(@RequestParam("image") MultipartFile image, @RequestParam("businessId") int businessId) {
        try {
            String imagePath = businessService.uploadBusinessImage(image, businessId);
            if (imagePath != null) {
                BusinessImageDTO businessImageDTO = new BusinessImageDTO(businessId, imagePath);
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Image uploaded successfully");
                responseDTO.setContent(businessImageDTO);
                return ResponseEntity.ok(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMesseage("Failed to upload image");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @GetMapping("/getBusinessImage/{businessId}")
    public ResponseEntity<ResponseDTO> getBusinessImage(@PathVariable int businessId) {
        try {
            Business business = businessService.findById(businessId);
            if (business != null && business.getMainBannerImagePath() != null) {
                BusinessImageDTO businessImageDTO = new BusinessImageDTO(businessId, business.getMainBannerImagePath());
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMesseage("Success");
                responseDTO.setContent(businessImageDTO);
                return ResponseEntity.ok(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMesseage("Business or image not found");
                responseDTO.setContent(null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchBusinesses(@RequestParam("query") String businessNameOrIndustryType,
                                                        @RequestParam("location") String cityOrDistrictOrZip) {
        try {
            List<Business> businesses = businessService.searchBusinesses(businessNameOrIndustryType, cityOrDistrictOrZip);
            List<SearchResultDTO> searchResults = businesses.stream()
                    .map(business -> modelMapper.map(business, SearchResultDTO.class))
                    .collect(Collectors.toList());

            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMesseage("Success");
            responseDTO.setContent(searchResults);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMesseage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }



//test comment
}
