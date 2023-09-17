package com.gym.controller;


import com.gym.entities.Trainer;
import com.gym.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

        @Autowired
        private TrainerService trainerService;

        @PostMapping("/create")
        public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
            Trainer createdTrainer = trainerService.createTrainer(trainer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
        }
    }


