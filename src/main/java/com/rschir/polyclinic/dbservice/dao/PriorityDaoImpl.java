package com.rschir.polyclinic.dbservice.dao;

import com.rschir.polyclinic.dbservice.dao.interfaces.PriorityDao;
import com.rschir.polyclinic.dbservice.entities.RecipePriority;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriorityDaoImpl implements PriorityDao {

    @Autowired
    private SessionFactory sessionFactory;

    public PriorityDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<RecipePriority> getAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<RecipePriority> theQuery =
                currentSession.createQuery("from RecipePriority", RecipePriority.class);
        List<RecipePriority> recipePriorities = theQuery.getResultList();
        return recipePriorities;
    }

    @Override
    public void save(RecipePriority recipePriority) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(recipePriority);
    }

    @Override
    public RecipePriority getById(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        RecipePriority recipePriority = currentSession.get(RecipePriority.class, theId);
        return recipePriority;
    }

    @Override
    public void remove(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery =
                currentSession.createQuery("delete from RecipePriority where priorityId=:priorityId");
        theQuery.setParameter("priorityId", theId);
        theQuery.executeUpdate();
    }
}
