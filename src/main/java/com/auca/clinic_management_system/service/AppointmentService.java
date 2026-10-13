package com.auca.clinic_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.entity.AppointmentStatus;
import com.auca.clinic_management_system.repository.AppointmentRepository;
import com.auca.clinic_management_system.repository.DoctorRepository;
import com.auca.clinic_management_system.repository.PatientRepository;
import com.auca.clinic_management_system.repository.PrescriptionRepository;

@Service 
public class AppointmentService {
    @Autowired 
    private AppointmentRepository appointmentRepository;
@Autowired 
private DoctorRepository doctorRepository;
@Autowired 
private PatientRepository patientRepository;

@Autowired 
private PrescriptionRepository prescriptionRepository;
    public List<Appointment> getallAppointments(){
        return appointmentRepository.findAll();
    }
    public Appointment getAppointmentById( Long id){
        return appointmentRepository.findById(id)
        .orElseThrow(()->new RuntimeException("Appointment not found with id:"+id));
    }
    public Appointment createAppointment(Appointment appointment){
         return appointmentRepository.save(appointment);
    }
    public Appointment updateAppointment(Long id,Appointment appointmentDetails){
        Appointment appointment=getAppointmentById(id);
        appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());

        return appointmentRepository.save(appointment);
    }
  public String deleteAppointment(Long id) {
    Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
    if (appointmentOptional.isEmpty()) {
        return "The appointment with that id does not exist";
    }
    if (prescriptionRepository.existsByAppointment_Id(id)) {
        return "That appointment has prescriptions, delete them first";
    }
    appointmentRepository.delete(appointmentOptional.get());
    return "Appointment deleted successfully";
}
  /*   public List<Appointment> getPendingByDoctor(Long doctorId) {
    return appointmentRepository.findByDoctorIdAndStatusOrderByAppointmentDateAsc(doctorId, "PENDING");
}

public List<Appointment> getPendingOverdue() {
    return appointmentRepository.pendingOverdue(LocalDate.now());
}*/

public List<Object[]> getAppointmentsPerDoctor() {
    return appointmentRepository.appointmentsPerDoctor();
}

public List<Appointment> getBySpecialization(String specName) {
    return appointmentRepository.byDoctorSpecialization(specName);
}

public Page<Appointment> getPagedByDoctor(Long doctorId, Pageable pageable) {
    return appointmentRepository.findByDoctorId(doctorId, pageable);
}
public List<Appointment> getByStatus(AppointmentStatus status) {
    return appointmentRepository.findByStatusOrderByAppointmentDateAsc(status);
}

public List<Appointment> getBetween(LocalDate start, LocalDate end) {
    return appointmentRepository.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start, end);
}

public  String saveAppointment(Appointment appointment){
    if (appointment.getAppointmentDate()==null || appointment.getAppointmentDate().isBefore(LocalDate.now())) {
        return "Appointment date cannot be in the past";
    }
    appointment.setStatus(AppointmentStatus.SCHEDULED);
    appointmentRepository.save(appointment);
    return "Appointment saved successfully";
}
public List<Object[]> getCountByStatus() {
    return appointmentRepository.countByStatus();
}
@Transactional
public String cancelDay(Long doctorId, LocalDate date) {
    int count = appointmentRepository.cancelDay(doctorId, date, AppointmentStatus.CANCELLED, AppointmentStatus.COMPLETED);
    return count + " appointments cancelled";
}
public Page<Appointment> getAppointmentsPage(Pageable pageable) {
    return appointmentRepository.findAll(pageable);
}
@Transactional
public String deleteCancelledBefore(LocalDate date) {
    int count = appointmentRepository.deleteCancelledBefore(date, AppointmentStatus.CANCELLED);
    return count + " appointments deleted";
}
public String updateStatus(Long id, AppointmentStatus updatedStatus){
    Optional<Appointment> appointmentOptional=appointmentRepository.findById(id);
    if (appointmentOptional.isEmpty()) {
        return "The appointment with that id does not exist";
    }
    Appointment appointment=appointmentOptional.get();
    AppointmentStatus current=appointment.getStatus();

    boolean allowed = false;

    if (current==AppointmentStatus.SCHEDULED && (updatedStatus==AppointmentStatus.CONFIRMED || updatedStatus == AppointmentStatus.CANCELLED)) {
        allowed=true;
    }
    else if (current==AppointmentStatus.CONFIRMED && (updatedStatus==AppointmentStatus.COMPLETED || updatedStatus==AppointmentStatus.CANCELLED)) {
        allowed=true;
    }
    if (!allowed) {
       return "Cannot change status from "+current+" to "+updatedStatus;
    }
    appointment.setStatus(updatedStatus);
    appointmentRepository.save(appointment);
    return "Appointment status updated successfully";
}
public String rescheduleAppointment(Long id , LocalDate toDaysDate){
    Optional<Appointment> appointmentOptional=appointmentRepository.findById(id);
    if (appointmentOptional.isEmpty()) {
       return "The appointment with that id does not exist"; 
    }
    Appointment appointment=appointmentOptional.get();
    if (appointment.getStatus()==AppointmentStatus.COMPLETED) {
        return "A COMPLETED appointment cannot be rescheduled";
        
    }
    if (appointment.getStatus()==AppointmentStatus.CANCELLED) {
        return "A CANCELLED appointment cannot be rescheduled";
        
    }
    if (toDaysDate.isBefore(LocalDate.now())) {
        return "Appointment date cannot be in the past";
        
    }
    boolean doctorBusy = appointmentRepository.existsByDoctorIdAndAppointmentDateAndStatusNot(appointment.getDoctor().getId(),toDaysDate,AppointmentStatus.CANCELLED);
    if (doctorBusy) {
        return "Doctor is already booked on that date ";
        
    }
    appointment.setAppointmentDate(toDaysDate);
    appointment.setStatus(AppointmentStatus.SCHEDULED);
    appointmentRepository.save(appointment);
    return "Appointment rescheduled successfully";
}
}
