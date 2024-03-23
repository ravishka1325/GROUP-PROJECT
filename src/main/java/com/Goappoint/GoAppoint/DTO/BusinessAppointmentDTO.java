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
        private String userFname; // add user's first name field
        private String userLname; // add user's last name field
        private int serviceId;
        private String serviceName;
        private int businessId;
        private LocalDateTime appointmentDateTime;

}
