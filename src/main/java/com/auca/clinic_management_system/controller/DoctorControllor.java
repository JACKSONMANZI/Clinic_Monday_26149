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

import com.auca.clinic_management_system.entity.Doctor;
import com.auca.clinic_management_system.service.DoctorService;

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
}
