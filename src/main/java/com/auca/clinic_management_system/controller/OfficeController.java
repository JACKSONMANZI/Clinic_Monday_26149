package com.auca.clinic_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.clinic_management_system.entity.Office;
import com.auca.clinic_management_system.service.OfficeService;

@RestController 
@RequestMapping ("/api/offices")
public class OfficeController {
     @Autowired
    private OfficeService officeService;

    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAllOffices();
    }

    @GetMapping("/{id}")
    public Office getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }

    @PostMapping
    public Office createOffice(@RequestBody Office office) {
        return officeService.createOffice(office);
    }
    @PutMapping("/{id}")
    public Office updateOffice(@PathVariable Long id, @RequestBody Office office) {
        return officeService.updateOffice(id, office);
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
    }
    @GetMapping("/busiest")
public ResponseEntity<?> getBusiestOffice() {
    List<Object[]> results = officeService.getBusiestOfficeStats();
    if (results.isEmpty()) {
        return ResponseEntity.ok("No appointments yet");
    }
    
    return ResponseEntity.ok(results.get(0));
}

}
