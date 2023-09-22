package com.gym.controller;


import com.gym.entities.Appointment;
import com.gym.entities.Trainer;
import com.gym.repository.AppointmentRepository;
import com.gym.repository.TrainerRepository;
import com.gym.service.AppointmentService;
import com.gym.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

        @Autowired
        private TrainerService trainerService;
        @Autowired
        private TrainerRepository trainerRepository;
        private AppointmentService appointmentService;
        @Autowired
        private AppointmentRepository appointmentRepository;

        @PostMapping("/create")
        public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
            Trainer createdTrainer = trainerService.createTrainer(trainer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
        }

    @GetMapping("/appointment/{trainerId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByTrainer(@PathVariable Long trainerId) {
        try {
            // Retrieve the trainer by ID
            Trainer trainer = trainerRepository.findById(trainerId)
                    .orElseThrow(() -> new EntityNotFoundException("Trainer not found with ID: " + trainerId));

            // Retrieve all appointments associated with the trainer
            List<Appointment> trainerAppointments = appointmentService.getAppointmentsByTrainer(trainer);

            return ResponseEntity.status(HttpStatus.OK).body(trainerAppointments);
        } catch (EntityNotFoundException e) {
            // Handle the case where the trainer is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    }


