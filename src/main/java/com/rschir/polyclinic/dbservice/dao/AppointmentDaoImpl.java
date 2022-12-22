package com.rschir.polyclinic.dbservice.dao;

import com.rschir.polyclinic.dbservice.dao.interfaces.AppointmentDao;
import com.rschir.polyclinic.dbservice.entities.Appointment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

//    @Autowired
    private SessionFactory sessionFactory;

    public AppointmentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Appointment theAppointment) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(theAppointment);
    }

    @Override
    public Appointment getById(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Appointment theAppointment = currentSession.get(Appointment.class, theId);
        return theAppointment;
    }

    @Override
    public List<Appointment> getAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Appointment> theQuery =
                currentSession.createQuery("from Appointment", Appointment.class);
        return theQuery.getResultList();
    }

    @Override
    public void remove(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery =
                currentSession.createQuery("delete from Appointment where appointmentId=:appointmentId");
        theQuery.setParameter("appointmentId", theId);
        theQuery.executeUpdate();
    }

    @Override
    public List<Appointment> filterByDescription(String description) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Appointment> theQuery =
                currentSession.createQuery(
                        "from Appointment where description like concat('%', :description, '%')", Appointment.class);
        theQuery.setParameter("description", description);
        return theQuery.getResultList();
    }

    @Override
    public List<Appointment> filterByPatientId(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Appointment> theQuery =
                currentSession.createQuery("from Appointment where patientId = :patientId", Appointment.class);
        theQuery.setParameter("patientId", theId);
        return theQuery.getResultList();
    }

    @Override
    public List<Appointment> filterByDoctorId(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Appointment> theQuery =
                currentSession.createQuery("from Appointment where doctorId = :doctorId", Appointment.class);
        theQuery.setParameter("doctorId", theId);
        return theQuery.getResultList();
    }
}
