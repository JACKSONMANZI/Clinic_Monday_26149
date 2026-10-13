package com.auca.clinic_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Doctor;
import com.auca.clinic_management_system.entity.Office;
import com.auca.clinic_management_system.entity.Specialization;
import com.auca.clinic_management_system.repository.DoctorRepository;
import com.auca.clinic_management_system.repository.OfficeRepository;
import com.auca.clinic_management_system.repository.SpecializationRepository;

@Service 
public class DoctorService {
    @Autowired 
    private DoctorRepository doctorRepository;
    @Autowired 
    private SpecializationRepository specializationRepository;
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
        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
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

public String addSpecializationToDoctor(Long doctorId,Long specializationId){
    Optional<Doctor> doctorOptional=doctorRepository.findById(doctorId);
    if(doctorOptional.isEmpty()){
        return "the doctor whith that id does not exist";
    }
    Optional<Specialization> specializationOptional=specializationRepository.findById(specializationId);
    if (specializationOptional.isEmpty()){
        return "That Specialization does not exist";      
    }
    Doctor doctor=doctorOptional.get();
    Specialization specialization=specializationOptional.get();

    if (doctor.getSpecializations().contains(specialization)){
        return "The doctor already has that specialization ";
        
    }
    doctor.getSpecializations().add(specialization);
    doctorRepository.save(doctor);
    return "Specialization added to the doctor";
}
public String removeSpecializationInDr(Long doctorId, Long specializationId){
    Optional<Doctor> doctorOptional=doctorRepository.findById(doctorId);
    if (doctorOptional.isEmpty()) {
        return "The doctor with that id does not exist";
        
    }
    Optional<Specialization> specializationOptional=specializationRepository.findById(specializationId);
    if (specializationOptional.isEmpty()) {
        return "That specialization does not exist";
        
    }
    Doctor doctor=doctorOptional.get();
    Specialization specialization=specializationOptional.get();

    if (!doctor.getSpecializations().contains(specialization)) {
        return "The doctor does not have that specialization";
        
    }
    doctor.getSpecializations().remove(specialization);
    doctorRepository.save(doctor);
    return "Specialization removed from the doctor.";
}
@Autowired 
private OfficeRepository officeRepository;
public String swapDoctorOffice(Long doctorId,Integer officeNumber){
    Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
    if (doctorOptional.isEmpty()) {
        return "The doctor with that id does not exist";
        
    } 
    Optional<Office> officeOptional = officeRepository.findByOfficeNumber(officeNumber);
    if (officeOptional.isEmpty()) {
        return "The office whith that office number does not exist";
        
    }
    Doctor doctor = doctorOptional.get();
    Office replaceOffice = officeOptional.get();

    boolean sameOffice=replaceOffice.equals(doctor.getOffice());
    if (!sameOffice && doctorRepository.existsByOffice_OfficeNumber(officeNumber)) {
        return "That office is taken by other doctor";
        
    }
    doctor.setOffice(replaceOffice);
    doctorRepository.save(doctor);
    return "Office changed successfully";
}
public List<Doctor> searchDoctors(String name){
    return doctorRepository.searchByName(name);
}
}
