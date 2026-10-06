package com.auca.clinic_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Patient;
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
}
