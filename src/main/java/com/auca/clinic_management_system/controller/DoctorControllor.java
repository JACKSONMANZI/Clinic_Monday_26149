package com.auca.clinic_management_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.clinic_management_system.entity.Appointment;
import com.auca.clinic_management_system.entity.Doctor;
import com.auca.clinic_management_system.service.AppointmentService;
import com.auca.clinic_management_system.service.DoctorService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping ("/api/doctors")
public class DoctorControllor {
     @Autowired 
     private DoctorService doctorService;

     @GetMapping 
     public  List<Doctor> getallDoctors(){
        return doctorService.getallDoctors();
     }
     @GetMapping("/{id}")
     public Doctor getDoctorbyId(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
     }
    @PostMapping 
    public Doctor createDoctor(@RequestBody Doctor doctor){
        return doctorService.createDoctor(doctor);
    }
    @PutMapping ("/{id}")
    public Doctor updateDoctor(@PathVariable  Long id, @RequestBody  Doctor doctor){
        return doctorService.updateDoctor(id, doctor);
    }
    @DeleteMapping ("/{id}")
    public void deleteDoctor(@PathVariable  Long id){
        doctorService.deleteDoctor(id);
    }
@GetMapping("/by-specialization")
public List<Doctor> getBySpecializationName(@RequestParam String name) {
    return doctorService.getBySpecializationName(name);
}
@GetMapping("/without-office")
public List<Doctor> getWithoutOffice() {
    return doctorService.getWithoutOffice();
}

@PutMapping ("/{doctorId}/specializations/{specializationId}")
public ResponseEntity<String> addSpecialization(@PathVariable Long doctorId,@PathVariable Long specializationId){
    String resulty=doctorService.addSpecializationToDoctor(doctorId, specializationId);
    if (resulty.equals("the doctor whith that id does not exist")){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resulty);
        
    }
    if (resulty.equals("That Specialization does not exist")){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resulty);
    }
    if (resulty.equals("The doctor already has that specialization " )){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resulty);
    }
    return ResponseEntity.ok(resulty);
}
@DeleteMapping ("/{doctorId}/specializations/{specializationId}")
public ResponseEntity<String> removeSpecializationNow(@PathVariable Long doctorId, @PathVariable  Long specializationId){
    String ouput = doctorService.removeSpecializationInDr(doctorId, specializationId);
    if (ouput.equals("The doctor with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ouput);
    }
    if (ouput.equals("That specialization does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ouput);
    }
    if (ouput.equals("The doctor does not have that specialization")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ouput);
    }
    return ResponseEntity.ok(ouput);
}
@PatchMapping ("/{doctorId}/office")
public ResponseEntity<String> swapDoctorOffice(@PathVariable Long doctorId,@RequestParam Integer officeNumber){
    String output = doctorService.swapDoctorOffice(doctorId, officeNumber);
    if (output.equals("The doctor with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
    }
    if (output.equals("The office whith that office number does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
    }
    if (output.equals("That office is taken by other doctor")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(output);
    }
    return ResponseEntity.ok(output);
}
@Autowired 
private AppointmentService appointmentService;
@GetMapping("/{doctorId}/agenda")
public ResponseEntity<?> getAgenda(@PathVariable Long doctorId, @RequestParam LocalDate date) {
    Optional<List<Appointment>> res = appointmentService.getDoctorAgenda(doctorId, date);

    if (res.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The doctor with that id does not exist");
    }
    return ResponseEntity.ok(res.get());
}
@GetMapping ("/search")
public List<Doctor> search(@RequestParam String name){
    return doctorService.searchDoctors(name);
}
}
