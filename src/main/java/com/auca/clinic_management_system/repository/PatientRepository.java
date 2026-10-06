package com.auca.clinic_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.clinic_management_system.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
