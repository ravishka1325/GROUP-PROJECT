package com.Goappoint.GoAppoint.Repo;

import com.Goappoint.GoAppoint.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByBusinessBusinessId(int businessId);
    List<Appointment> findByUserUserID(int userID);
    long countByBusinessBusinessIdAndAppointmentDateTimeBetween(int businessId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}