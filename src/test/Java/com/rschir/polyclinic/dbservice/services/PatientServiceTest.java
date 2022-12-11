package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.config.AppConfig;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.services.interfaces.PatientService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {

    private static PatientService patientService;

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        patientService = context.getBean("patientServiceImpl", PatientService.class);
    }

    @AfterAll
    static void tearDown() {
        patientService = null;
    }

    @Test
    void save() {

        // create test data
        Patient patient = new Patient("TestName", "TestSurname", "TestPatronymic", "TestPhone");
        // save test data
        patientService.save(patient);

        // test
        assertNotNull(patient, "the patient must be not null");
        assertNotNull(patient.getPatientId(), "the patientId must be not null");

        // remove test data
        patientService.remove(patient.getPatientId());
    }

    @Test
    void getById() {

        // create test data
        Patient patient = new Patient("TestName", "TestSurname", "TestPatronymic", "TestPhone");
        // save test data
        patientService.save(patient);

        // test
        Long patientId = patient.getPatientId();
        patient = patientService.getById(patientId);
        assertNotNull(patient, "the patient must be not null");

        // remove test data
        patientService.remove(patient.getPatientId());

    }

    @Test
    void getAll() {

        // test
        List<Patient> patients = patientService.getAll();
        assertNotNull(patients, "the list must be not null");
        assertTrue(patients.size() > 0, "the list must have more than 0 elements");
    }

    @Test
    void remove() {

        // create test data
        Patient patient = new Patient("TestName", "TestSurname", "TestPatronymic", "TestPhone");
        // save test data
        patientService.save(patient);

        // test
        Long patientId = patient.getPatientId();
        patientService.remove(patient.getPatientId());
        patient = patientService.getById(patientId);
        assertNull(patient, "the patient must be null after removing");

    }
}