package com.auca.clinic_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<List<Prescription>> getPrescriptionsByAppointment(Long appointmentId){
        if (!appointmentRepository.existsById(appointmentId)) {
            return Optional.empty();
        }
        return  Optional.of(prescriptionRepository.findByAppointment_Id(appointmentId));

    }
    public  List<Prescription> getPrescriptionByPatient(String ssNumber){
        return prescriptionRepository.findByPatientSsNumber(ssNumber);
    }
    public String updatePrescription(UUID id ,Prescription prescriptionDetails){
        Optional<Prescription> prescriptOptional=prescriptionRepository.findById(id);
        if (prescriptOptional.isEmpty()) {
            return  "The prescription with that id does not exist";
            
        }
        Prescription pr=prescriptOptional.get();
        pr.setMedicineName(prescriptionDetails.getMedicineName());
        pr.setDosage(prescriptionDetails.getDosage());
        pr.setDurationDays(prescriptionDetails.getDurationDays());
        prescriptionRepository.save(pr);
        return "Prescription updated successfully";
    }
    public  String deletePrescription(UUID id){
        Optional<Prescription> prescriptionOptional=prescriptionRepository.findById(id);
        if ((prescriptionOptional.isEmpty())) {
            return "The prescription with that id does not exist";
            
        }
        prescriptionRepository.delete(prescriptionOptional.get());
        return  "Prescription deleted successfully";
    }
}
