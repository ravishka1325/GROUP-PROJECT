package com.Goappoint.GoAppoint.Service;

import com.Goappoint.GoAppoint.DTO.*;
import com.Goappoint.GoAppoint.Entity.Business;
import com.Goappoint.GoAppoint.Repo.BusinessRepo;
import com.Goappoint.GoAppoint.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BusinessService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BusinessRepo businessRepo;

    public String saveBusiness(BusinessDTO businessDTO){
        if(businessRepo.existsById(businessDTO.getBusinessId())){
            return VarList.RSP_DUPLICATED;
        }else{
            businessRepo.save(modelMapper.map(businessDTO, Business.class));
            return VarList.RSP_SUCCESS;
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Business authenticate(String businessEmail, String businessPassword) {
        Business business = businessRepo.findByBusinessEmail(businessEmail);
        if (business != null && passwordEncoder.matches(businessPassword, business.getBusinessPassword())) {
            return business;
        }
        return null;
    }

    public String updateBusiness(BpageDTO bpageDTO) {
        if (businessRepo.existsById(bpageDTO.getBusinessId())) {
            Business existingBusiness = businessRepo.findById(bpageDTO.getBusinessId()).get();

            if (bpageDTO.getBusinessName() != null) {
                existingBusiness.setBusinessName(bpageDTO.getBusinessName());
            }else {
                existingBusiness.setBusinessName("");
            }
            if (bpageDTO.getOwnerName() != null) {
                existingBusiness.setOwnerName(bpageDTO.getOwnerName());
            }else {
                existingBusiness.setOwnerName("");
            }
            if (bpageDTO.getBusinessAddress() != null) {
                existingBusiness.setBusinessAddress(bpageDTO.getBusinessAddress());
            }else {
                existingBusiness.setBusinessAddress("");
            }

            if (bpageDTO.getBusinessAbout() != null) {
                existingBusiness.setBusinessAbout(bpageDTO.getBusinessAbout());
            } else {
                existingBusiness.setBusinessAbout("");
            }

            if (bpageDTO.getBusinessMobile() != null) {
                existingBusiness.setBusinessMobile(bpageDTO.getBusinessMobile());
            } else {
                existingBusiness.setBusinessMobile(existingBusiness.getBusinessMobile());
            }

            businessRepo.save(existingBusiness);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public Business findById(int businessId) {
        return businessRepo.findById(businessId).orElse(null);
    }

    public String updateBusinessHours(BusinessHoursDTO businessHoursDTO) {
        if (businessRepo.existsById(businessHoursDTO.getBusinessId())) {
            Business existingBusiness = businessRepo.findById(businessHoursDTO.getBusinessId()).get();
            existingBusiness.setBusinessHours(businessHoursDTO.getBusinessHours());
            businessRepo.save(existingBusiness);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }




    @Value("${app.image.upload.dir}")
    private String imageUploadDir;

    public String uploadBusinessImage(MultipartFile image, int businessId) {
        try {
            Business business = findById(businessId);
            if (business != null) {
                String fileName = generateUniqueFileName(image.getOriginalFilename());
                String filePath = imageUploadDir + File.separator + fileName;
                File destinationFile = new File(filePath);
                image.transferTo(destinationFile);

                business.setMainBannerImagePath("images/" + fileName);
                businessRepo.save(business);

                return business.getMainBannerImagePath();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString();
        return fileName + fileExtension;
    }


    public List<Business> searchBusinesses(String businessNameOrIndustryType, String cityOrDistrictOrZip) {
        return businessRepo.findByBusinessNameContainingOrBusinessIndustryTypeContainingAndBusinessCityContainingOrBusinessDistrictContainingOrBusinessZipContaining(businessNameOrIndustryType, businessNameOrIndustryType, cityOrDistrictOrZip, cityOrDistrictOrZip, cityOrDistrictOrZip);
    }



}
