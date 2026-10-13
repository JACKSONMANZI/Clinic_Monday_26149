package com.auca.clinic_management_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.clinic_management_system.entity.Office;

public interface OfficeRepository extends JpaRepository<Office,Long> {
 
    @Query("SELECT o FROM Office o LEFT JOIN Doctor d ON d.office = o WHERE  d IS NULL ORDER BY o.officeNumber")
    List<Office> findFreeOffices();

    Optional<Office> findByOfficeNumber(Integer officeNumber);
}
