package com.auca.clinic_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.entity.AppointmentStatus;
import com.auca.clinic_management_system.entity.Patient;
import com.auca.clinic_management_system.repository.AppointmentRepository;
import com.auca.clinic_management_system.repository.DoctorRepository;
import com.auca.clinic_management_system.repository.PatientRepository;

@Service 
public class PatientService {
@Autowired 
private PatientRepository patientRepository;
public List<Patient> getAllPatients(){
    return patientRepository.findAll();
}
public Patient getPatientById( Long id){
    return patientRepository.findById(id)
    .orElseThrow(()-> new RuntimeException("Patient not found with id:"+id));
}
public Patient createPatient(Patient patient){
    return patientRepository.save(patient);
}  
public Patient updatePatient(Long id,Patient patientDetails){
Patient patient= getPatientById(id);
patient.setFirstName(patientDetails.getFirstName());
patient.setLastName(patientDetails.getLastName());
patient.setDateOfBirth(patientDetails.getDateOfBirth());
patient.setGender(patientDetails.getGender());
patient.setPhone(patientDetails.getPhone());
return patientRepository.save(patient);
} 
public void deletePatient(Long id){
    Patient patient=getPatientById(id);
    patientRepository.delete(patient);
}
public List<Patient> getPatientsByLastName(String lastName) {
    return patientRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
}
@Autowired
private AppointmentRepository appointmentRepository;
@Autowired
private DoctorRepository doctorRepository;

public boolean doctorExists(Long doctorId) {
    return doctorRepository.existsById(doctorId);
}

public List<Patient> getPatientsOfDoctor(Long doctorId) {
    return appointmentRepository.findPatientsByDoctorId(doctorId);
}
public List<Patient> getFrequentPatients(long min) {
    return appointmentRepository.findFrequentPatients(min);
}
public Optional<List<Appointment>> getUpcomingAppointments(String ssNumber) {
    if (patientRepository.findBySsNumber(ssNumber).isEmpty()) {
        return Optional.empty();
    }
    List<AppointmentStatus> allowed = List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED);
    return Optional.of(appointmentRepository
            .findByPatient_SsNumberAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(
                    ssNumber, LocalDate.now(), allowed));
}
}
