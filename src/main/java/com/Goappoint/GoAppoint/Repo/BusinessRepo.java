package com.Goappoint.GoAppoint.Repo;

import com.Goappoint.GoAppoint.Entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepo extends JpaRepository<Business,Integer> {
    Business findByBusinessEmail(String businessEmail);
    List<Business> findByBusinessNameContainingOrBusinessIndustryTypeContainingAndBusinessCityContainingOrBusinessDistrictContainingOrBusinessZipContaining(String businessName, String industryType, String city, String district, String zip);
}
