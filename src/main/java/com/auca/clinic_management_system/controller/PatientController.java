package com.auca.clinic_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.clinic_management_system.entity.Patient;
import com.auca.clinic_management_system.service.PatientService;

@RestController 
@RequestMapping ("/api/patients")
public class PatientController {
    @Autowired 
private PatientService patientService;

@GetMapping 
public List<Patient> getallPatients(){
    return patientService.getAllPatients();
}
@GetMapping ("/{id}")
public Patient getPatientbyId(@PathVariable Long id)   {
    return patientService.getPatientById(id);
}
@PostMapping 
public Patient createPatient(@RequestBody Patient patient){
return patientService.createPatient(patient);
}
@PutMapping ("/{id}")
public Patient updatePatient(@PathVariable Long id,@RequestBody Patient patient){
    return patientService.updatePatient(id, patient);
}
@DeleteMapping ("/{id}")
public void  deletePatient(@PathVariable Long id){
    patientService.deletePatient(id);
}
}
