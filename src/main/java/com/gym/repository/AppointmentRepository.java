package com.gym.repository;

import com.gym.entities.Appointment;
import com.gym.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Custom method to find overlapping appointments for a trainer and date/time
    @Query("SELECT a FROM Appointment a WHERE a.trainer = :trainer " +
            "AND a.dateTime <= :endDateTime AND :startDateTime <= a.dateTime")
    List<Appointment> findOverlappingAppointments(@Param("trainer") Trainer trainer, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

    List<Appointment> findByTrainerIdAndDateTime(Long trainerId, LocalDateTime dateTime);

    Appointment findAppointmentById(Long appointmentId);
}

