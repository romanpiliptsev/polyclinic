package com.rschir.polyclinic.dbservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rschir.polyclinic.dbservice.entities.Doctor;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.entities.Recipe;
import com.rschir.polyclinic.dbservice.entities.RecipePriority;
import com.rschir.polyclinic.dbservice.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PriorityService priorityService;
    private ObjectMapper mapper = new ObjectMapper();



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
}
