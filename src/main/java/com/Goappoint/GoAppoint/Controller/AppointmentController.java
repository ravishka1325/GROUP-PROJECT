package com.Goappoint.GoAppoint.Controller;

import com.Goappoint.GoAppoint.DTO.AppointmentDTO;
import com.Goappoint.GoAppoint.DTO.BusinessAppointmentDTO;
import com.Goappoint.GoAppoint.DTO.ResponseDTO;
import com.Goappoint.GoAppoint.Entity.Appointment;
import com.Goappoint.GoAppoint.Service.AppointmentService;
import com.Goappoint.GoAppoint.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/appointment")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<ResponseDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment createdAppointment = appointmentService.createAppointment(appointmentDTO);
            ResponseDTO responseDTO = new ResponseDTO(VarList.RSP_SUCCESS, "Appointment created successfully", mapAppointmentToDTO(createdAppointment));
            return ResponseEntity.ok(responseDTO);
        } catch (Exception ex) {
            ResponseDTO errorResponseDTO = new ResponseDTO(VarList.RSP_ERROR, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
        }
    }

    private AppointmentDTO mapAppointmentToDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getAppointmentId(),
                appointment.getUser().getUserID(),
                appointment.getService().getServiceId(),
                appointment.getBusiness().getBusinessId(),
                appointment.getAppointmentDateTime()
        );
    }

    @GetMapping("/getAppointment/{businessId}")
    public ResponseEntity<ResponseDTO> getAppointmentsByBusinessId(@PathVariable int businessId) {
        try {
            List<BusinessAppointmentDTO> appointments = appointmentService.getAppointmentsByBusinessId(businessId);
            ResponseDTO responseDTO = new ResponseDTO(VarList.RSP_SUCCESS, "Appointments retrieved successfully", appointments);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception ex) {
            ResponseDTO errorResponseDTO = new ResponseDTO(VarList.RSP_ERROR, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
        }
    }

}