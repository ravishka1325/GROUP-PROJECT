package com.Goappoint.GoAppoint.Service;

import com.Goappoint.GoAppoint.DTO.AppointmentDTO;
import com.Goappoint.GoAppoint.DTO.BusinessAppointmentDTO;
import com.Goappoint.GoAppoint.Entity.Appointment;
import com.Goappoint.GoAppoint.Entity.Business;
import com.Goappoint.GoAppoint.Entity.Service;
import com.Goappoint.GoAppoint.Entity.User;
import com.Goappoint.GoAppoint.Repo.AppointmentRepo;
import com.Goappoint.GoAppoint.Repo.BusinessRepo;
import com.Goappoint.GoAppoint.Repo.ServiceRepo;
import com.Goappoint.GoAppoint.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private BusinessRepo businessRepo;

    public Appointment createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = mapDTOToEntity(appointmentDTO);

        User user = userRepo.findById(appointmentDTO.getUserId()).orElseThrow();
        Service service = serviceRepo.findById(appointmentDTO.getServiceId()).orElseThrow();
        Business business = businessRepo.findById(appointmentDTO.getBusinessId()).orElseThrow();

        appointment.setUser(user);
        appointment.setService(service);
        appointment.setBusiness(business);

        return appointmentRepo.save(appointment);
    }

    private Appointment mapDTOToEntity(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentDTO.getAppointmentId());
        appointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
        return appointment;
    }

    //Get Appointments by Business ID
    public List<BusinessAppointmentDTO> getAppointmentsByBusinessId(int businessId) {
        List<Appointment> appointments = appointmentRepo.findByBusinessBusinessId(businessId);
        return appointments.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<BusinessAppointmentDTO> getAppointmentsByUserId(int userId) {
        List<Appointment> appointments = appointmentRepo.findByUserUserID(userId); // Change 'userId' to 'userID'
        return appointments.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }


    private BusinessAppointmentDTO mapEntityToDTO(Appointment appointment) {
        User user = appointment.getUser();
        return new BusinessAppointmentDTO(
                appointment.getAppointmentId(),
                user.getUserID(),
                user.getUserFname(),
                user.getUserLname(),
                user.getUserMobile(),
                appointment.getService().getServiceId(),
                appointment.getService().getServiceName(),
                appointment.getBusiness().getBusinessId(),
                appointment.getBusiness().getBusinessName(),
                appointment.getAppointmentDateTime()
        );
    }

    public long getAppointmentCountByBusinessIdAndDate(int businessId, LocalDate date) {
        return appointmentRepo.countByBusinessBusinessIdAndAppointmentDateTimeBetween(
                businessId,
                date.atStartOfDay(),
                date.atTime(23, 59, 59)
        );
    }


}