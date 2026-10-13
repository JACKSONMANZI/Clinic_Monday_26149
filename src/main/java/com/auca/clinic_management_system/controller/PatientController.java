package com.auca.clinic_management_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.clinic_management_system.entity.Appointment;
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
@GetMapping("/by-last-name")
public List<Patient> getPatientsByLastName(@RequestParam  String lastName) {
    return patientService.getPatientsByLastName(lastName);
}
@GetMapping("/of-doctor/{doctorId}")
public ResponseEntity<?> getPatientsOfDoctor(@PathVariable Long doctorId) {
    if (!patientService.doctorExists(doctorId)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The doctor with that id does not exist");
    }
    return ResponseEntity.ok(patientService.getPatientsOfDoctor(doctorId));
}
@GetMapping("/frequent")
public List<Patient> getFrequentPatients(@RequestParam long min) {
    return patientService.getFrequentPatients(min);
}
@GetMapping("/{ssNumber}/upcoming")
public ResponseEntity<?> getUpcoming(@PathVariable String ssNumber) {
    Optional<List<Appointment>> result = patientService.getUpcomingAppointments(ssNumber);

    if (result.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("That patient with that ssNumber does not exist");
    }
    return ResponseEntity.ok(result.get());
}
}
