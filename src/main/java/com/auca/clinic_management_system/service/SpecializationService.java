package com.auca.clinic_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Specialization;
import com.auca.clinic_management_system.repository.SpecializationRepository;

@Service 
public class SpecializationService {

    @Autowired 
    private SpecializationRepository specializationRepository;

    public List<Specialization> getAllSpecialization(){
        return specializationRepository.findAll();
    }

    public Specialization getSpecializationById(Long id){
        return specializationRepository.findById(id)
        .orElseThrow(()->new RuntimeException("Specialization not found with id: " + id));
    }
    public  Specialization createSpecialization(Specialization specialization){
        return specializationRepository.save(specialization);
    }
    public Specialization updateSpecialization(Long id,Specialization specializationDetails){
        Specialization specialization=getSpecializationById(id);
        specialization.setName(specializationDetails.getName());
        specialization.setDescription(specializationDetails.getDescription());
        return specializationRepository.save(specialization);
    }
    public void deleteSpecialization(Long id){
        Specialization specialization=getSpecializationById(id);
        specializationRepository.delete(specialization);
    }
}
