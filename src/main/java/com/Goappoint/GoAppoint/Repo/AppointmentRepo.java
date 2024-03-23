package com.Goappoint.GoAppoint.Repo;

import com.Goappoint.GoAppoint.Entity.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByBusinessBusinessId(int businessId);
    List<Appointment> findByAppointmentDateTimeBefore(LocalDateTime dateTime);
}