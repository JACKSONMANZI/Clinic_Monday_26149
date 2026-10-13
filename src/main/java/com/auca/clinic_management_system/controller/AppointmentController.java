package com.auca.clinic_management_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.auca.clinic_management_system.entity.AppointmentStatus;
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
   /*  @GetMapping("/doctor/{doctorId}/pending")
public List<Appointment> pendingByDoctor(@PathVariable Long doctorId) {
    return appointmentService.getPendingByDoctor(doctorId);
}

@GetMapping("/pending-overdue")
public List<Appointment> pendingOverdue() {
    return appointmentService.getPendingOverdue();
}*/

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

@GetMapping("/by-status")
public List<Appointment> getByStatus(@RequestParam AppointmentStatus status) {
    return appointmentService.getByStatus(status);
}

@GetMapping("/between")
public List<Appointment> getBetween(@RequestParam String start, @RequestParam String end) {
    return appointmentService.getBetween(LocalDate.parse(start), LocalDate.parse(end));
}
@PostMapping("/save")
public ResponseEntity<String> saveAppointment(@RequestBody Appointment appointment) {
    String result = appointmentService.saveWithCheck(appointment);
    if (result.startsWith("Doctor is already booked")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
    return ResponseEntity.ok(result);
}
@GetMapping("/stats/by-status")
public List<Object[]> getCountByStatus() {
    return appointmentService.getCountByStatus();
}
@PatchMapping("/cancel-day")
public String cancelDay(@RequestParam Long doctorId, @RequestParam String date) {
    return appointmentService.cancelDay(doctorId, LocalDate.parse(date));
}
@GetMapping("/page")
public Page<Appointment> getAppointmentsPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "appointmentDate,desc") String sort) {
    String[] parts = sort.split(",");
    Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
            ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, parts[0]));
    return appointmentService.getAppointmentsPage(pageable);
}
@DeleteMapping("/cancelled-before")
public String deleteCancelledBefore(@RequestParam String date) {
    return appointmentService.deleteCancelledBefore(LocalDate.parse(date));
}
}
