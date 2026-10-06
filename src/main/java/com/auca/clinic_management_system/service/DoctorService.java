package com.auca.clinic_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Doctor;
import com.auca.clinic_management_system.repository.DoctorRepository;

@Service 
public class DoctorService {
    @Autowired 
    private DoctorRepository doctorRepository;
    public List<Doctor> getallDoctors(){
        return doctorRepository.findAll();
    }
    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id)
        .orElseThrow(() ->new RuntimeException("Doctor not found with id:"+id));
    }
    public Doctor createDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }
    public Doctor updateDoctor(Long id, Doctor doctorDetails ){
        Doctor doctor= getDoctorById(id);
        doctor.setName(doctorDetails.getName());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setPhone(doctorDetails.getPhone());
        doctor.setOffice(doctorDetails.getOffice());
        doctor.setSpecializations(doctorDetails.getSpecializations());
        return doctorRepository.save(doctor);
    }
    public void deleteDoctor(Long id){
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }
    
public List<Doctor> getBySpecializationName(String name) {
    return doctorRepository.findBySpecializationName(name);
}
public List<Doctor> getWithoutOffice() {
    return doctorRepository.findWithoutOffice();
}
}
