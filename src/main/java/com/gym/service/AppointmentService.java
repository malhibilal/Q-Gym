package com.gym.service;

import com.gym.entities.*;
import com.gym.repository.AppointmentRepository;
import com.gym.repository.TrainerRepository;
import com.gym.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    // to book an appointment
    public Appointment bookAppointment(Appointment appointment) {
        // You can directly use the provided appointment object
        User user = appointment.getUser();
        Trainer trainer = appointment.getTrainer();
        LocalDateTime dateTime = appointment.getDateTime();
        String location = appointment.getLocation();
        String notes = appointment.getNotes();

        // Find the user by ID
        User foundUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + user.getId()));

        // Find the trainer by ID
        Trainer foundTrainer = trainerRepository.findById(trainer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with ID: " + trainer.getId()));

        // Check trainer's availability
        if (!isTrainerAvailable(foundTrainer.getId(), dateTime)) {
            throw new TrainerNotAvailableException("Trainer is not available at the specified date and time.");
        }

        // Create and save the appointment
        Appointment newAppointment = new Appointment();
        newAppointment.setUser(foundUser);
        newAppointment.setTrainer(foundTrainer);
        newAppointment.setDateTime(dateTime);
        newAppointment.setLocation(location);
        newAppointment.setNotes(notes);

        return appointmentRepository.save(newAppointment);
    }

    // to update an appointment
    public Appointment updateAppointment(Appointment appointment) {
        // Find the appointment by ID
        Long appointmentId = appointment.getId();
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));

        // Validate if the appointment belongs to the specified user
        Long userId = appointment.getUser().getId();
        if (!userId.equals(existingAppointment.getUser().getId())) {
            throw new UnauthorizedUserException("User is not authorized to update this appointment.");
        }

        // Check if the selected trainer is available at the specified date and time
        Long trainerId = appointment.getTrainer().getId();
        LocalDateTime dateTime = appointment.getDateTime();
        if (!isTrainerAvailable(trainerId, dateTime)) {
            throw new TrainerNotAvailableException("Selected trainer is not available at the specified date and time.");
        }

        // Update the appointment details
        existingAppointment.setTrainer(appointment.getTrainer());
        existingAppointment.setDateTime(dateTime);
        existingAppointment.setLocation(appointment.getLocation());
        existingAppointment.setNotes(appointment.getNotes());

        return appointmentRepository.save(existingAppointment);
    }



    // to delete an appointment
    public void deleteAppointment(Long appointmentId) {
        // Find the appointment by ID
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));

        // Delete the appointment
        appointmentRepository.delete(appointment);
    }

    private boolean isTrainerAvailable(Long trainerId, LocalDateTime dateTime) {
        // Query the trainer's appointments for the specified trainerId and dateTime
        List<Appointment> trainerAppointments = appointmentRepository.findByTrainerIdAndDateTime(trainerId, dateTime);

        // Check if there are any existing appointments for the trainer at the same dateTime
        return trainerAppointments.isEmpty();
    }


}

