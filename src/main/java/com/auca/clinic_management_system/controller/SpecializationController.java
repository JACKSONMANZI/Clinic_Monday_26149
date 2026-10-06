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

import com.auca.clinic_management_system.entity.Specialization;
import com.auca.clinic_management_system.service.SpecializationService;

@RestController 
@RequestMapping ("/api/specializations") 
public class SpecializationController {
    @Autowired 
    private SpecializationService specializationService;

    @GetMapping 
    public List<Specialization> getAllSpecialization(){
        return specializationService.getAllSpecialization();
    }
    @GetMapping ("/{id}")
    public Specialization getSpecializationById(@PathVariable  Long id){
        return specializationService.getSpecializationById(id);
    }
    @PostMapping 
    public Specialization createSpecialization(@RequestBody Specialization specialization){
        return specializationService.createSpecialization(specialization);
    }
    @PutMapping ("/{id}")
    public Specialization updateSpecialization(@PathVariable Long id,@RequestBody Specialization specializationDetails){
        return specializationService.updateSpecialization(id, specializationDetails);
    }
    @DeleteMapping ("/{id}")
    public void deleteSpecialization(@PathVariable Long id){
        specializationService.deleteSpecialization(id);
    }
    @GetMapping("/unused")
public List<Specialization> getUnused() {
    return specializationService.getUnused();
}
}
