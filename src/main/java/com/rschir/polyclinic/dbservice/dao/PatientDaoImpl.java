package com.rschir.polyclinic.dbservice.dao;

import com.rschir.polyclinic.dbservice.dao.interfaces.PatientDao;
import com.rschir.polyclinic.dbservice.entities.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDaoImpl implements PatientDao {

    @Autowired
    private SessionFactory sessionFactory;

    public PatientDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Patient thePatient) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(thePatient);
    }

    @Override
    public Patient getById(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Patient thePatient = currentSession.get(Patient.class, theId);
        return thePatient;
    }

    @Override
    public List<Patient> getAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Patient> theQuery =
                currentSession.createQuery("from Patient order by lastName", Patient.class);
        List<Patient> patients = theQuery.getResultList();
        return patients;
    }

    @Override
    public void remove(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery =
                currentSession.createQuery("delete from Patient where patientId=:patientId");
        theQuery.setParameter("patientId", theId);
        theQuery.executeUpdate();
    }

    @Override
    public Patient getByPolicy(String thePolicy) {
        Session currentSession = null;
        try {
            currentSession = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            currentSession = sessionFactory.openSession();
        }
        Query<Patient> theQuery =
                currentSession.createQuery("from Patient where policy=:policy");
        theQuery.setParameter("policy", thePolicy);
        return theQuery.getResultList().get(0);
    }
}
