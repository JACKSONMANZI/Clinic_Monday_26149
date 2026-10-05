package com.auca.clinic_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.clinic_management_system.entity.Office;

public interface OfficeRepository extends JpaRepository<Office,Long> {
    
}
