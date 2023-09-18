package com.gym.service;

import com.gym.entities.Trainer;
import com.gym.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    public Trainer createTrainer(Trainer trainer){
       Trainer newTrainer= this.trainerRepository.save(trainer);
       return newTrainer;
    }
}
