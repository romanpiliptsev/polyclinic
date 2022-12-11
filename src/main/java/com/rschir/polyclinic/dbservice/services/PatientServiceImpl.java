package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.dbservice.dao.interfaces.PatientDao;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.services.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao hibernatePatientDao;

    @Override
    @Transactional
    public void save(Patient patient) {
        hibernatePatientDao.save(patient);
    }

    @Override
    @Transactional
    public Patient getById(Long id) {
        return hibernatePatientDao.getById(id);
    }

    @Override
    @Transactional
    public List<Patient> getAll() {
        return hibernatePatientDao.getAll();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        hibernatePatientDao.remove(id);
    }

    @Override
    @Transactional
    public Patient getByPolicy(String policy) {
        return hibernatePatientDao.getByPolicy(policy);
    }
}
