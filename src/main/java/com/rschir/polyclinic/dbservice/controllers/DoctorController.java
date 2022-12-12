package com.rschir.polyclinic.dbservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rschir.polyclinic.dbservice.entities.Doctor;
import com.rschir.polyclinic.dbservice.services.interfaces.DoctorService;
import com.rschir.polyclinic.dbservice.services.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    private ObjectMapper mapper = new ObjectMapper();



    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public List<Doctor> doctors() {
        return doctorService.getAll();
    }

    @RequestMapping(value = "/stuff/doctor/{id}", method = RequestMethod.GET)
    public Doctor doctor(@PathVariable("id") long id) {
        return doctorService.getById(id);
    }

    @RequestMapping(value = "/stuff/admin/add_doctor", method = RequestMethod.POST)
    public void addDoctor(@RequestBody String doctorStr) {
        Doctor doctor = null;
        try {
            doctor = mapper.readValue(doctorStr, Doctor.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        doctorService.save(doctor);
    }

    @RequestMapping(value = "/stuff/admin/modify_doctor", method = RequestMethod.PUT)
    public void modifyDoctor(@RequestBody String doctorStr) {
        Doctor doctor = null;
        try {
            doctor = mapper.readValue(doctorStr, Doctor.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        doctorService.save(doctor);
    }

    @RequestMapping(value = "/stuff/admin/remove_doctor/{id}", method = RequestMethod.DELETE)
    public void removeDoctor(@PathVariable("id") long id) {
        doctorService.remove(id);
    }
}
