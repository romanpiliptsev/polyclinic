package com.rschir.polyclinic.dbservice.dao.interfaces;

import com.rschir.polyclinic.dbservice.entities.Appointment;

import java.util.List;

public interface AppointmentDao extends Dao<Appointment> {
    List<Appointment> filterByDescription(String description);
    List<Appointment> filterByPatientId(Long patientId);
    List<Appointment> filterByDoctorId(Long doctorId);
}
