package com.auca.clinic_management_system.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity 
@Data 
public class Appointment {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne 
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;

    @ManyToOne 
    @JoinColumn (name = "patient_id")
    private Patient patient;

}
