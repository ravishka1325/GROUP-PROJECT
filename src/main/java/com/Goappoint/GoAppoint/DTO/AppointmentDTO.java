package com.Goappoint.GoAppoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private int appointmentId;
    private int userId;
    private int serviceId;
    private int businessId;
    private LocalDateTime appointmentDateTime;
}