package com.Goappoint.GoAppoint.DTO;

import com.Goappoint.GoAppoint.Entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDTO {
    private int serviceId;
    private String serviceName;
    private double servicePrice;
    private int businessId;

    public static ServiceDTO fromEntity(Service service) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServiceId(service.getServiceId());
        serviceDTO.setServiceName(service.getServiceName());
        serviceDTO.setServicePrice(service.getServicePrice());
        return serviceDTO;
    }
}
