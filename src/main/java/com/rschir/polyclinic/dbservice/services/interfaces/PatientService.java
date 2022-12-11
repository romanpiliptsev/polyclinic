package com.rschir.polyclinic.dbservice.services.interfaces;

import com.rschir.polyclinic.dbservice.entities.Patient;
import org.springframework.transaction.annotation.Transactional;

public interface PatientService extends Service<Patient> {
    @Transactional
    Patient getByPolicy(String policy);
}
