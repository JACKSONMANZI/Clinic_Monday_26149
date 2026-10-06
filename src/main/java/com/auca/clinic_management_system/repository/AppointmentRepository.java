package com.auca.clinic_management_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.entity.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{
//Pending appointments
List<Appointment>findByDoctorIdAndStatusOrderByAppointmentDateAsc(Long doctorId, String status);
//PENDING appointments whose date has already passed

@Query("""
    SELECT a FROM Appointment a 
    JOIN FETCH a.doctor d 
    JOIN FETCH a.patient p 
    WHERE a.status='PENDING' AND a.appointmentDate<:today
""")

List<Appointment> pendingOverdue(@Param("today") LocalDate today);

//Aggregate: number of appointments per doctor

 @Query("""
        SELECT d.name, COUNT(a)
        FROM Appointment a JOIN a.doctor d
        GROUP BY d.name
        ORDER BY COUNT(a) DESC
        """)
    List<Object[]> appointmentsPerDoctor();

    //appointments of doctors with a given specialization
    //JOIN a.doctor follows @ManyToOne; JOIN d.specializations walks @ManyToMany

    @Query("""
        SELECT a FROM Appointment a
        JOIN a.doctor d
        JOIN d.specializations s
        WHERE s.name = :specName
        """)
    List<Appointment> byDoctorSpecialization(@Param("specName") String specName);

    //Pagination: a doctor's appointments, 10 per page
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

    
List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);


List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(LocalDate start, LocalDate end);


boolean existsByDoctorIdAndAppointmentDateAndStatusNot(Long doctorId, LocalDate date, AppointmentStatus status);
    }
