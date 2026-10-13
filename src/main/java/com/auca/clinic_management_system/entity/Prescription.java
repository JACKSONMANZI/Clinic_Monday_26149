package com.auca.clinic_management_system.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Entity 
@Data 
public class Prescription {
    @Id 
    @GeneratedValue 

    private UUID id;
    private String medicineName;
    private String dosage;
    private int durationDays;
    private LocalDate issuedDate;

    @ManyToOne 
    @JoinColumn (name = "appointment_id")
    private Appointment appointment;
    
}
