package com.auca.clinic_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.clinic_management_system.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
@Query("SELECT d FROM Doctor d JOIN d.specializations s WHERE LOWER(s.name) = LOWER(:name)")
List<Doctor> findBySpecializationName(@Param("name") String name);

@Query("SELECT d FROM Doctor d WHERE d.office IS NULL ORDER BY d.firstName,d.lastName")
List<Doctor> findWithoutOffice();
}
