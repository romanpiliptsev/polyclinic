package com.rschir.polyclinic.dbservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rschir.polyclinic.dbservice.entities.Appointment;
import com.rschir.polyclinic.dbservice.entities.Doctor;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    private ObjectMapper mapper = new ObjectMapper();




    @RequestMapping(value = "/stuff/appointments", method = RequestMethod.GET)
    public List<Appointment> appointments() {
        return appointmentService.getAll();
    }

    @RequestMapping(value = "/stuff/appointments", params = "desc", method = RequestMethod.GET)
    public List<Appointment> appointmentsFilteredByDesc(@RequestParam("desc") String desc) {
        return appointmentService.filterByDescription(desc);
    }

    @RequestMapping(value = "/stuff/appointments", params = "patient_id", method = RequestMethod.GET)
    public List<Appointment> appointmentsFilteredByPatientId(@RequestParam("patient_id") long patient_id) {
        return appointmentService.filterByPatientId(patient_id);
    }

    @RequestMapping(value = "/user/appointments", params = "patient_policy", method = RequestMethod.GET)
    public List<Appointment> appointmentsFilteredByPatientPolicy(@RequestParam("patient_policy") String patient_policy,
                                                                 Authentication authentication) {
        Patient patient = patientService.getByPolicy(patient_policy);
        if (!Objects.equals(patient.getPolicy(), authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return appointmentService.filterByPatientId(patient.getPatientId());
    }

    @RequestMapping(value = "/stuff/appointments", params = "doctor_id", method = RequestMethod.GET)
    public List<Appointment> appointmentsFilteredByDoctorId(@RequestParam("doctor_id") long doctor_id) {
        return appointmentService.filterByDoctorId(doctor_id);
    }

    @RequestMapping(value = "/stuff/appointment/{id}", method = RequestMethod.GET)
    public Appointment appointment(@PathVariable("id") long id, Authentication authentication) {
        return appointmentService.getById(id);
    }

    @RequestMapping(value = "/user/appointment/{id}", method = RequestMethod.GET)
    public Appointment appointmentForUsers(@PathVariable("id") long id, Authentication authentication) {
        Appointment appointment = appointmentService.getById(id);
        if (!Objects.equals(appointment.getPatient().getPatientId(), Long.valueOf(authentication.getName()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return appointmentService.getById(id);
    }

    @RequestMapping(value = "/stuff/registry/add_appointment", params = {"desc", "patient_id", "doctor_id"}, method = RequestMethod.POST)
    public void addAppointment(@RequestParam("desc") String desc, @RequestParam("patient_id") long patient_id,
                               @RequestParam("doctor_id") long doctor_id) {
        Doctor doctor = doctorService.getById(doctor_id);
        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Patient patient = patientService.getById(patient_id);
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        appointmentService.save(new Appointment(desc, doctor, patient));
    }

    @RequestMapping(value = "/stuff/registry/add_appointment", params = {"patient_id", "doctor_id"}, method = RequestMethod.POST)
    public void addAppointmentWithRequestBody(@RequestBody String appointmentStr,
                                              @RequestParam(value = "patient_id", required = false) long patient_id,
                                              @RequestParam(value = "doctor_id", required = false) long doctor_id) {
        Appointment appointment = null;
        try {
            appointment = mapper.readValue(appointmentStr, Appointment.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (appointment.getDoctor() == null) {
            Doctor doctor = doctorService.getById(doctor_id);
            if (doctor == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            appointment.setDoctor(doctor);
        }
        if (appointment.getPatient() == null) {
            Patient patient = patientService.getById(patient_id);
            if (patient == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            appointment.setPatient(patient);
        }
        appointmentService.save(appointment);
    }

    @RequestMapping(value = "/stuff/registry/modify_appointment", method = RequestMethod.PUT)
    public void modifyAppointment(@RequestBody String appointmentStr) {
        Appointment appointment = null;
        try {
            appointment = mapper.readValue(appointmentStr, Appointment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        appointmentService.save(appointment);
    }

    @RequestMapping(value = "/stuff/doc/set_appointment_desc", method = RequestMethod.PUT)
    public void setAppointmentDesc(@RequestParam(value = "appointment_id") long appointment_id,
                                   @RequestParam(value = "desc") String desc,
                                   Authentication authentication) {
        Appointment appointment = appointmentService.getById(appointment_id);
        if (!Objects.equals(appointment.getDoctor().getDoctorId(), Long.valueOf(authentication.getName()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        appointment.setDescription(desc);
        appointmentService.save(appointment);
    }

    @RequestMapping(value = "/stuff/registry/remove_appointment/{id}", method = RequestMethod.DELETE)
    public void removeAppointment(@PathVariable("id") long id) {
        appointmentService.remove(id);
    }
}
