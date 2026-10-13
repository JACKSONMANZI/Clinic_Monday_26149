package com.auca.clinic_management_system.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.entity.AppointmentStatus;
import com.auca.clinic_management_system.entity.Prescription;
import com.auca.clinic_management_system.repository.AppointmentRepository;
import com.auca.clinic_management_system.repository.PrescriptionRepository;

@Service 
public class PrescriptionService {
    @Autowired 
    private PrescriptionRepository prescriptionRepository;

    @Autowired 
    private AppointmentRepository appointmentRepository;

    public  String savePrescription(Long appointmentId, Prescription prescription){
     Optional<Appointment> appointmentOptional=appointmentRepository.findById(appointmentId);
     if (appointmentOptional.isEmpty()) {
        return "The appointment with that id does not exist";
        
     }   
     Appointment appointment=appointmentOptional.get();
     if(appointment.getStatus()!=AppointmentStatus.COMPLETED){
        return "Prescriptions are only for COMPLETED  appointments";
     }
     prescription.setIssuedDate(LocalDate.now());
     prescription.setAppointment(appointment);
     prescriptionRepository.save(prescription);
     return "Prescription saved successfully";
    }
}
