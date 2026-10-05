package com.auca.clinic_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.auca.clinic_management_system.service.AppointmentService;

@RestController 
@RequestMapping ("/api/appointments")
public class AppointmentController {
    @Autowired 
    private AppointmentService appointmentService;

    @GetMapping 
    public List<Appointment> getallAppointments(){
        return appointmentService.getallAppointments();
    } 
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id){
        return appointmentService.getAppointmentById(id);
    }
    @PostMapping 
    public Appointment createAppointment(@RequestBody Appointment appointment){
        return appointmentService.createAppointment(appointment);
    }
    @PutMapping ("/{id}")
    public Appointment updateAppointment(@PathVariable Long id,@RequestBody Appointment appointment){
        return appointmentService.updateAppointment(id, appointment);
    }
    @DeleteMapping ("/{id}")
    public void deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
    }
    @GetMapping("/doctor/{doctorId}/pending")
public List<Appointment> pendingByDoctor(@PathVariable Long doctorId) {
    return appointmentService.getPendingByDoctor(doctorId);
}

@GetMapping("/pending-overdue")
public List<Appointment> pendingOverdue() {
    return appointmentService.getPendingOverdue();
}

@GetMapping("/per-doctor")
public List<Object[]> appointmentsPerDoctor() {
    return appointmentService.getAppointmentsPerDoctor();
}

@GetMapping("/specialization/{specName}")
public List<Appointment> bySpecialization(@PathVariable String specName) {
    return appointmentService.getBySpecialization(specName);
}

@GetMapping("/doctor/{doctorId}/page")
public Page<Appointment> pagedByDoctor(@PathVariable Long doctorId,
        @RequestParam(defaultValue = "0") int page) {
    Pageable pageable = PageRequest.of(page, 10, Sort.by("appointmentDate"));
    return appointmentService.getPagedByDoctor(doctorId, pageable);
}
}
