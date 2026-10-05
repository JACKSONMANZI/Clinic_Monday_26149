package com.auca.clinic_management_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.repository.AppointmentRepository;

@Service 
public class AppointmentService {
    @Autowired 
    private AppointmentRepository appointmentRepository;
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
        appointment.setStatus(appointmentDetails.getStatus());

        return appointmentRepository.save(appointment);
    }
    public void deleteAppointment(Long id){
        Appointment appointment=getAppointmentById(id);

        appointmentRepository.delete(appointment);
    }
    public List<Appointment> getPendingByDoctor(Long doctorId) {
    return appointmentRepository.findByDoctorIdAndStatusOrderByAppointmentDateAsc(doctorId, "PENDING");
}

public List<Appointment> getPendingOverdue() {
    return appointmentRepository.pendingOverdue(LocalDate.now());
}

public List<Object[]> getAppointmentsPerDoctor() {
    return appointmentRepository.appointmentsPerDoctor();
}

public List<Appointment> getBySpecialization(String specName) {
    return appointmentRepository.byDoctorSpecialization(specName);
}

public Page<Appointment> getPagedByDoctor(Long doctorId, Pageable pageable) {
    return appointmentRepository.findByDoctorId(doctorId, pageable);
}
}
