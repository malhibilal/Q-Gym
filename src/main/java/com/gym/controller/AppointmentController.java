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
}



