package com.auca.clinic_management_system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.clinic_management_system.service.DashBordService;

@RestController 
@RequestMapping ("/api/dashboard")
public class DashBoardController {
@Autowired 
private  DashBordService dashBordService;

@GetMapping ("/summary")
public  Map<String,Long> bringSummary(){
    return dashBordService.getSummary();
}
    
}
