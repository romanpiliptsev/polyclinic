package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.config.AppConfig;
import com.rschir.polyclinic.dbservice.entities.Doctor;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.entities.Recipe;
import com.rschir.polyclinic.dbservice.entities.RecipePriority;
import com.rschir.polyclinic.dbservice.services.interfaces.DoctorService;
import com.rschir.polyclinic.dbservice.services.interfaces.PatientService;
import com.rschir.polyclinic.dbservice.services.interfaces.PriorityService;
import com.rschir.polyclinic.dbservice.services.interfaces.RecipeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    private static DoctorService doctorService;
    private static PatientService patientService;
    private static RecipeService recipeService;
    private static PriorityService priorityService;

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        doctorService = context.getBean("doctorServiceImpl", DoctorServiceImpl.class);
        patientService = context.getBean("patientServiceImpl", PatientServiceImpl.class);
        recipeService = context.getBean("recipeServiceImpl", RecipeServiceImpl.class);
        priorityService = context.getBean("priorityServiceImpl", PriorityServiceImpl.class);
    }

    @AfterAll
    static void tearDown() {
        recipeService = null;
    }

    @Test
    void save() {

        // create test data
        Patient patient = new Patient("TestName", "TestSurname", "TestPatronymic", "TestPhone");
        Doctor doctor = new Doctor("TestName", "TestSurname", "TestPatronymic", "TestSpec");
        RecipePriority priority = priorityService.getAll().get(0);
        Recipe recipe = new Recipe("TestDesc", new Date(), 111, doctor, patient, priority);

        // save test data
        doctorService.save(doctor);
        patientService.save(patient);
        recipeService.save(recipe);

        // test
        assertNotNull(recipe, "the recipe must be not null");
        assertNotNull(recipe.getRecipeId(), "the recipeId must be not null");

        // remove test data
        recipeService.remove(recipe.getRecipeId());
        doctorService.remove(doctor.getDoctorId());
        patientService.remove(patient.getPatientId());
    }

    @Test
    void getById() {

        // create test data
        Patient patient = new Patient("TestName", "TestSurname", "TestPatronymic", "TestPhone");
        Doctor doctor = new Doctor("TestName", "TestSurname", "TestPatronymic", "TestSpec");
        RecipePriority priority = priorityService.getAll().get(0);
        Recipe recipe = new Recipe("TestDesc", new Date(), 111, doctor, patient, priority);

        // save test data
        doctorService.save(doctor);
        patientService.save(patient);
        recipeService.save(recipe);

        // test
        Long recipeId = recipe.getRecipeId();
        recipe = recipeService.getById(recipeId);
        assertNotNull(recipe, "the recipe must be not null");
        assertNotNull(recipe.getDoctor(), "the doctor must be not null");
        assertNotNull(recipe.getPatient(), "the patient must be not null");


        // remove test data
        recipeService.remove(recipe.getRecipeId());
        doctorService.remove(doctor.getDoctorId());
        patientService.remove(patient.getPatientId());

    }

    @Test
    void getAll() {

        // test
        List<Recipe> recipes = recipeService.getAll();
        assertNotNull(recipes, "the list must be not null");
        assertTrue(recipes.size() > 0, "the list must have more than 0 elements");
        System.out.println(recipes.get(0).getDoctor());
    }

    @Test
    void remove() {

        // create test data
        Patient patient = new Patient("TestName", "TestSurname", "TestPatronymic", "TestPhone");
        Doctor doctor = new Doctor("TestName", "TestSurname", "TestPatronymic", "TestSpec");
        RecipePriority priority = priorityService.getAll().get(0);
        Recipe recipe = new Recipe("TestDesc", new Date(), 111, doctor, patient, priority);

        // save test data
        doctorService.save(doctor);
        patientService.save(patient);
        recipeService.save(recipe);

        // test
        Long recipeId = recipe.getRecipeId();
        recipeService.remove(recipe.getRecipeId());
        recipe = recipeService.getById(recipeId);
        assertNull(recipe, "the recipe must be null after removing");

        // remove test data
        doctorService.remove(doctor.getDoctorId());
        patientService.remove(patient.getPatientId());

    }
}