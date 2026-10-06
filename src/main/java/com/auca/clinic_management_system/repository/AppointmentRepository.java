package com.auca.clinic_management_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.entity.AppointmentStatus;
import com.auca.clinic_management_system.entity.Patient;

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
   
@Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.id = :doctorId")
List<Patient> findPatientsByDoctorId(@Param("doctorId") Long doctorId);

@Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
List<Object[]> countByStatus();

@Query("SELECT a.patient FROM Appointment a GROUP BY a.patient HAVING COUNT(a) >= :min ORDER BY COUNT(a) DESC")
List<Patient> findFrequentPatients(@Param("min") long min);

@Query("SELECT o.roomNumber, COUNT(a) FROM Appointment a JOIN a.doctor d JOIN d.office o GROUP BY o.roomNumber ORDER BY COUNT(a) DESC")
List<Object[]> findBusiestOfficeStats();

@Modifying
@Transactional 
@Query("UPDATE Appointment a SET a.status = :cancelled WHERE a.doctor.id = :doctorId AND a.appointmentDate = :date AND a.status <> :completed")
int cancelDay(@Param("doctorId") Long doctorId, @Param("date") LocalDate date,
              @Param("cancelled") AppointmentStatus cancelled, @Param("completed") AppointmentStatus completed);

@Modifying
@Transactional
@Query("DELETE FROM Appointment a WHERE a.status = :cancelled AND a.appointmentDate < :date")
int deleteCancelledBefore(@Param("date") LocalDate date, @Param("cancelled") AppointmentStatus cancelled);
}
