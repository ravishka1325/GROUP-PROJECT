package com.Goappoint.GoAppoint.Service;

import com.Goappoint.GoAppoint.DTO.ServiceDTO;
import com.Goappoint.GoAppoint.DTO.UserDTO;
import com.Goappoint.GoAppoint.Entity.Business;
import com.Goappoint.GoAppoint.Entity.Service;
import com.Goappoint.GoAppoint.Entity.User;
import com.Goappoint.GoAppoint.Repo.BusinessRepo;
import com.Goappoint.GoAppoint.Repo.ServiceRepo;
import com.Goappoint.GoAppoint.Repo.UserRepo;
import com.Goappoint.GoAppoint.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private BusinessRepo businessRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    public String saveService(ServiceDTO serviceDTO) {
        if (serviceRepo.existsById(serviceDTO.getServiceId())) {
            return VarList.RSP_DUPLICATED;
        } else {
            Business business = businessRepo.findById(serviceDTO.getBusinessId())
                    .orElseThrow(() -> new RuntimeException("Business not found"));

            Service service = modelMapper.map(serviceDTO, Service.class);
            service.setBusiness(business);
            serviceRepo.save(service);
            return VarList.RSP_SUCCESS;
        }
    }

    public List<ServiceDTO> getServicesByBusinessId(int businessId) {
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        List<Service> services = business.getServices();
        return services.stream()
                .map(ServiceDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
