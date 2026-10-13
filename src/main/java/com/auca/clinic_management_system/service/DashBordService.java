package com.auca.clinic_management_system.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.AppointmentStatus;
import com.auca.clinic_management_system.repository.AppointmentRepository;
import com.auca.clinic_management_system.repository.DoctorRepository;
import com.auca.clinic_management_system.repository.OfficeRepository;
import com.auca.clinic_management_system.repository.PatientRepository;
import com.auca.clinic_management_system.repository.PrescriptionRepository;

@Service 
public class DashBordService {
    @Autowired 
    private DoctorRepository doctorRepository;
    @Autowired 
    private PatientRepository patientRepository;
    @Autowired 
    private OfficeRepository officeRepository;
    @Autowired 
    private AppointmentRepository appointmentRepository;
    @Autowired 
    private PrescriptionRepository prescriptionRepository;

     public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("patients", patientRepository.count());
        summary.put("doctors", doctorRepository.count());
        summary.put("freeOffices", (long) officeRepository.findFreeOffices().size());
        summary.put("appointmentsToday", (long) appointmentRepository
                .findByAppointmentDateBetweenOrderByAppointmentDateAsc(LocalDate.now(), LocalDate.now()).size());
        summary.put("scheduled", appointmentRepository.countByStatus(AppointmentStatus.SCHEDULED));
        summary.put("prescriptions", prescriptionRepository.count());
        return summary;
    }   
}
