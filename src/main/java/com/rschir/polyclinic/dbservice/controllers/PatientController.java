package com.rschir.polyclinic.dbservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;
    private ObjectMapper mapper = new ObjectMapper();



    @RequestMapping(value = "/stuff/patients", method = RequestMethod.GET)
    public List<Patient> patients() {
        return patientService.getAll();
    }

    @RequestMapping(value = "/stuff/patient/{id}", method = RequestMethod.GET)
    public Patient patient(@PathVariable("id") long id) {
        return patientService.getById(id);
    }

    @RequestMapping(value = "/stuff/registry/add_patient", method = RequestMethod.POST)
    public void addPatient(@RequestBody String patientStr) {
        Patient patient = null;
        try {
            patient = mapper.readValue(patientStr, Patient.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        patientService.save(patient);
    }

    @RequestMapping(value = "/stuff/registry/modify_patient", method = RequestMethod.PUT)
    public void modifyPatient(@RequestBody String patientStr) {
        Patient patient = null;
        try {
            patient = mapper.readValue(patientStr, Patient.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        patientService.save(patient);
    }

    @RequestMapping(value = "/stuff/registry/remove_patient/{id}", method = RequestMethod.DELETE)
    public void removePatient(@PathVariable("id") long id) {
        patientService.remove(id);
    }
}
