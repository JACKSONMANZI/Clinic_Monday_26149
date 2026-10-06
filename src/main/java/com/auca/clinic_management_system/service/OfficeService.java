package com.auca.clinic_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.clinic_management_system.entity.Office;
import com.auca.clinic_management_system.repository.AppointmentRepository;
import com.auca.clinic_management_system.repository.OfficeRepository;

@Service 
public class OfficeService {
    @Autowired 
    private OfficeRepository officeRepository;
     public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    public Office getOfficeById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));
    }

    public Office createOffice(Office office) {
        return officeRepository.save(office);
    }
     public Office updateOffice(Long id, Office officeDetails) {
        Office office = getOfficeById(id);
        office.setRoomNumber(officeDetails.getRoomNumber());
        return officeRepository.save(office);
    }

    public void deleteOffice(Long id) {
        Office office = getOfficeById(id);
        officeRepository.delete(office);
    }
    @Autowired
private AppointmentRepository appointmentRepository;

public List<Object[]> getBusiestOfficeStats() {
    return appointmentRepository.findBusiestOfficeStats();
}
}

