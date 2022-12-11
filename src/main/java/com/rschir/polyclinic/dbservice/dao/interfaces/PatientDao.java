package com.rschir.polyclinic.dbservice.dao.interfaces;

import com.rschir.polyclinic.dbservice.entities.Patient;

public interface PatientDao extends Dao<Patient> {
    Patient getByPolicy(String policy);
}
