package com.Goappoint.GoAppoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessAppointmentDTO {

        private int appointmentId;
        private int userId;
        private String userFname;
        private String userLname;
        private String userMobile;
        private int serviceId;
        private String serviceName;
        private int businessId;
        private String businessName;
        private LocalDateTime appointmentDateTime;

}
