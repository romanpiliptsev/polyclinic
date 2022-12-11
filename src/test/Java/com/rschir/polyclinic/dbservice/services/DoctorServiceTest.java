package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.config.AppConfig;
import com.rschir.polyclinic.dbservice.entities.Doctor;
import com.rschir.polyclinic.dbservice.services.interfaces.DoctorService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoctorServiceTest {

    private static DoctorService doctorService;

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        doctorService = context.getBean("doctorServiceImpl", DoctorService.class);
    }

    @AfterAll
    static void tearDown() {
        doctorService = null;
    }

    @Test
    void save() {

        // create test data
        Doctor doctor = new Doctor("TestName", "TestSurname", "TestPatronymic", "TestSpec");
        // save test data
        doctorService.save(doctor);

        // test
        assertNotNull(doctor, "the doctor must be not null");
        assertNotNull(doctor.getDoctorId(), "the doctorId must be not null");

        // remove test data
        doctorService.remove(doctor.getDoctorId());
    }

    @Test
    void getById() {

        // create test data
        Doctor doctor = new Doctor("TestName", "TestSurname", "TestPatronymic", "TestSpec");
        // save test data
        doctorService.save(doctor);

        // test
        Long doctorId = doctor.getDoctorId();
        doctor = doctorService.getById(doctorId);
        assertNotNull(doctor, "the doctor must be not null");

        // remove test data
        doctorService.remove(doctor.getDoctorId());

    }

    @Test
    void getAll() {

        // test
        List<Doctor> doctors = doctorService.getAll();
        assertNotNull(doctors, "the list must be not null");
        assertTrue(doctors.size() > 0, "the list must have more than 0 elements");
    }

    @Test
    void remove() {

        // create test data
        Doctor doctor = new Doctor("TestName", "TestSurname", "TestPatronymic", "TestSpec");
        // save test data
        doctorService.save(doctor);

        // test
        Long doctorId = doctor.getDoctorId();
        doctorService.remove(doctor.getDoctorId());
        doctor = doctorService.getById(doctorId);
        assertNull(doctor, "the doctor must be null after removing");

    }
}