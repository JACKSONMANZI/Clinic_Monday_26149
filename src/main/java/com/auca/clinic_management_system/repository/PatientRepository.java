package com.auca.clinic_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.clinic_management_system.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    
}
