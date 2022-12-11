package com.rschir.polyclinic.dbservice.services.interfaces;

import com.rschir.polyclinic.dbservice.entities.Appointment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppointmentService extends Service<Appointment> {

    @Transactional
    List<Appointment> filterByDescription(String description);

    @Transactional
    List<Appointment> filterByPatientId(Long patientId);

    @Transactional
    List<Appointment> filterByDoctorId(Long doctorId);
}
