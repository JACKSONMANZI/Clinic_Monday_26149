package com.auca.clinic_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.clinic_management_system.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription,UUID> {
    
}
