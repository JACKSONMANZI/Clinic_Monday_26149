package com.auca.clinic_management_system.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity 
@Data 
public class Patient {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
     private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
}
