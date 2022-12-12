package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.dbservice.dao.interfaces.DoctorDao;
import com.rschir.polyclinic.dbservice.dao.interfaces.PatientDao;
import com.rschir.polyclinic.dbservice.entities.Doctor;
import com.rschir.polyclinic.dbservice.services.interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService, UserDetailsService {

    @Autowired
    private DoctorDao hibernateDoctorDao;
    @Autowired
    private PatientDao hibernatePatientDao;

    @Override
    @Transactional
    public void save(Doctor doctor) {
        hibernateDoctorDao.save(doctor);
    }

    @Override
    @Transactional
    public Doctor getById(Long id) {
        return hibernateDoctorDao.getById(id);
    }

    @Override
    @Transactional
    public List<Doctor> getAll() {
        return hibernateDoctorDao.getAll();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        hibernateDoctorDao.remove(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s.length() < 4) {
            return hibernateDoctorDao.getById(Long.valueOf(s));
        } else {
            return hibernatePatientDao.getByPolicy(s);
        }
    }
}
