package com.auca.clinic_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.clinic_management_system.entity.Prescription;
import com.auca.clinic_management_system.service.PrescriptionService;

@RestController 
@RequestMapping ("/api/prescriptions")
public class PrescriptionController {
    @Autowired 
    private PrescriptionService prescriptionService;

    @PostMapping ("/save")
    public ResponseEntity<String> savePrescription(@RequestParam Long appointmentId,@RequestBody Prescription prescription){
        String output=prescriptionService.savePrescription(appointmentId, prescription);
        if (output.equals("The appointment with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
        if (output.equals("Prescriptions are only for COMPLETED  appointments")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
