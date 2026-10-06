package com.auca.clinic_management_system.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@JsonIncludeProperties({"hibernateLazyInitializer", "handler"})
@Entity 
@Data 
public class Doctor {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

     @OneToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToMany 
    @JoinTable (name = "doctor_specialization",joinColumns =@JoinColumn (name="doctor_id"),inverseJoinColumns = @JoinColumn (name="specialization_id"))
    private Set<Specialization> specializations;
}
