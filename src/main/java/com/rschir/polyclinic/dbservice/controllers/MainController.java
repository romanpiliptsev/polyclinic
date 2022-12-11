package com.rschir.polyclinic.dbservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rschir.polyclinic.dbservice.entities.*;
import com.rschir.polyclinic.dbservice.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
public class MainController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PriorityService priorityService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private AppointmentService appointmentService;

    private ObjectMapper mapper = new ObjectMapper();

// Все работники: 1) Список пациентов; 2) Пациент по айди; 3) Список докторов; 5) Доктор по айди; 6) Список рецептов + фильтры;
// 7) Рецепт по айди; 8) Список приемов + фильтры; 9) Прием по айди; 10) Список приоритетов рецептов.

// Доктора: 1) Добавление рецепта; 2) Изменение рецепта; 3) Удаление рецепт; 4) Редактирование описания приема.

// Регистратура: 1) Добавление пациента; 2) Изменение пациента; 3) Удаление пациента; 4) Добавление приема;
// 5) Изменение приема; 6) Удаление приема.

// Админ: все из регистратуры + 1) Добавление доктора; 2) Изменение доктора; 3) Удаление доктора.

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



    @RequestMapping(value = "/stuff/recipes", method = RequestMethod.GET)
    public List<Recipe> recipes() {
        return recipeService.getAll();
    }

    @RequestMapping(value = "/stuff/recipes", params = "desc", method = RequestMethod.GET)
    public List<Recipe> recipesFilteredByDesc(@RequestParam("desc") String desc) {
        return recipeService.filterByDescription(desc);
    }

    @RequestMapping(value = "/stuff/recipes", params = "patient_id", method = RequestMethod.GET)
    public List<Recipe> recipesFilteredByPatientId(@RequestParam("patient_id") long patient_id) {
        return recipeService.filterByPatientId(patient_id);
    }

    @RequestMapping(value = "/user/recipes", params = "patient_policy", method = RequestMethod.GET)
    public List<Recipe> recipesFilteredByPatientPolicy(@RequestParam("patient_policy") String patient_policy,
                                                       Authentication authentication) {
        Patient patient = patientService.getByPolicy(patient_policy);
        if (!patient.getPolicy().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return recipeService.filterByPatientId(patient.getPatientId());
    }

    @RequestMapping(value = "/stuff/recipes", params = "doctor_id", method = RequestMethod.GET)
    public List<Recipe> recipesFilteredByDoctorId(@RequestParam("doctor_id") long doctor_id) {
        return recipeService.filterByDoctorId(doctor_id);
    }

    @RequestMapping(value = "/stuff/recipes", params = "priority_id", method = RequestMethod.GET)
    public List<Recipe> recipesFilteredByPriorityId(@RequestParam("priority_id") long priority_id) {
        return recipeService.filterByPriorityId(priority_id);
    }

    @RequestMapping(value = "/stuff/recipe/{id}", method = RequestMethod.GET)
    public Recipe recipe(@PathVariable("id") long id) {
        return recipeService.getById(id);
    }

    @RequestMapping(value = "/stuff/doc/add_recipe", params = {"desc", "validity", "patient_id", "doctor_id", "priority_id"},
            method = RequestMethod.POST)
    public void addRecipe(@RequestParam("desc") String desc, @RequestParam("validity") int validity,
                          @RequestParam("patient_id") long patient_id, @RequestParam("doctor_id") long doctor_id,
                          @RequestParam("priority_id") long priority_id) {
        Doctor doctor = doctorService.getById(doctor_id);
        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Patient patient = patientService.getById(patient_id);
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        RecipePriority priority = priorityService.getById(priority_id);
        if (priority == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        recipeService.save(new Recipe(desc, validity, doctor, patient, priority));
    }

    @RequestMapping(value = "/stuff/doc/add_recipe", method = RequestMethod.POST)
    public void addRecipeWithRequestBody(@RequestBody String recipeStr,
                                         @RequestParam(value = "patient_id", required = false) long patient_id,
                                         @RequestParam(value = "doctor_id", required = false) long doctor_id,
                                         @RequestParam(value = "priority_id", required = false) long priority_id) {
        Recipe recipe = null;
        try {
            recipe = mapper.readValue(recipeStr, Recipe.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (recipe.getDoctor() == null) {
            Doctor doctor = doctorService.getById(doctor_id);
            if (doctor == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            recipe.setDoctor(doctor);
        }
        if (recipe.getPatient() == null) {
            Patient patient = patientService.getById(patient_id);
            if (patient == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            recipe.setPatient(patient);
        }
        if (recipe.getRecipePriority() == null) {
            RecipePriority priority = priorityService.getById(priority_id);
            if (priority == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            recipe.setRecipePriority(priority);
        }
        recipeService.save(recipe);
    }

    @RequestMapping(value = "/stuff/doc/modify_recipe", method = RequestMethod.PUT)
    public void modifyRecipe(@RequestBody String recipeStr) {
        Recipe recipe = null;
        try {
            recipe = mapper.readValue(recipeStr, Recipe.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        recipeService.save(recipe);
    }

    @RequestMapping(value = "/stuff/doc/remove_recipe/{id}", method = RequestMethod.DELETE)
    public void removeRecipe(@PathVariable("id") long id) {
        recipeService.remove(id);
    }



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



    @RequestMapping(value = "/stuff/recipe_priorities", method = RequestMethod.GET)
    public List<RecipePriority> recipePriorities() {
        return priorityService.getAll();
    }
}
