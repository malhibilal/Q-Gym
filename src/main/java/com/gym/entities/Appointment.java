package com.gym.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

        @Entity
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public class Appointment {
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;
                private LocalDateTime dateTime;
                private String location;
                private String notes;
                @ManyToOne
                private User user;

                @ManyToOne
                private Trainer trainer;


        }
