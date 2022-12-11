package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.dbservice.dao.interfaces.AppointmentDao;
import com.rschir.polyclinic.dbservice.entities.Appointment;
import com.rschir.polyclinic.dbservice.services.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDao hibernateAppointmentDao;

    @Override
    @Transactional
    public void save(Appointment appointment) {
        hibernateAppointmentDao.save(appointment);
    }

    @Override
    @Transactional
    public Appointment getById(Long id) {
        return hibernateAppointmentDao.getById(id);
    }

    @Override
    @Transactional
    public List<Appointment> getAll() {
        return hibernateAppointmentDao.getAll();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        hibernateAppointmentDao.remove(id);
    }

    @Override
    @Transactional
    public List<Appointment> filterByDescription(String description) {
        return hibernateAppointmentDao.filterByDescription(description);
    }

    @Override
    @Transactional
    public List<Appointment> filterByPatientId(Long patientId) {
        return hibernateAppointmentDao.filterByPatientId(patientId);
    }

    @Override
    @Transactional
    public List<Appointment> filterByDoctorId(Long doctorId) {
        return hibernateAppointmentDao.filterByDoctorId(doctorId);
    }
}
