package com.gym.controller;

import com.gym.entities.Appointment;

import com.gym.entities.TrainerNotAvailableException;

import com.gym.repository.TrainerRepository;
import com.gym.repository.UserRepository;
import com.gym.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody Appointment appointment) {
        Map<String, Object> response = new HashMap<>();

        try {
            Appointment bookedAppointment = appointmentService.bookAppointment(appointment);
            response.put("message", "Appointment booked successfully.");
            response.put("data", bookedAppointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TrainerNotAvailableException e) {
            response.put("message", "Trainer not available at the specified date and time.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put("message", "User or trainer not found.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<Map<String, Object>> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody Appointment updatedAppointment
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);

            if (appointment == null) {
                response.put("message", "Appointment not found with ID: " + appointmentId);
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            updatedAppointment.setId(appointmentId); // Set the ID for the updated appointment

            // Call the service method to update the appointment
            Appointment updated = appointmentService.updateAppointment(updatedAppointment);

            response.put("message", "Appointment updated successfully.");
            response.put("data", updated);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (TrainerNotAvailableException e) {
            response.put("message", "Trainer not available at the specified date and time.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<Map<String, String>> deleteAppointment(@PathVariable Long appointmentId) {
        Map<String, String> response = new HashMap<>();

        try {
            appointmentService.deleteAppointment(appointmentId);
            response.put("message", "Appointment deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.put("message", "Appointment not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }




}



