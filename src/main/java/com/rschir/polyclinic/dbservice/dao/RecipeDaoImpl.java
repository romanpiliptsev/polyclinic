package com.rschir.polyclinic.dbservice.dao;

import com.rschir.polyclinic.dbservice.dao.interfaces.RecipeDao;
import com.rschir.polyclinic.dbservice.entities.Recipe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecipeDaoImpl implements RecipeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public RecipeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Recipe theRecipe) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(theRecipe);
    }

    @Override
    public Recipe getById(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Recipe theRecipe = currentSession.get(Recipe.class, theId);
        return theRecipe;
    }

    @Override
    public List<Recipe> getAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Recipe> theQuery =
                currentSession.createQuery("from Recipe", Recipe.class);
        return theQuery.getResultList();
    }

    @Override
    public void remove(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery =
                currentSession.createQuery("delete from Recipe where recipeId=:recipeId");
        theQuery.setParameter("recipeId", theId);
        theQuery.executeUpdate();
    }

    @Override
    public Map<String, Long> getCountOfRecipesByDoctor() {
        Session currentSession = sessionFactory.getCurrentSession();

        Map<String, Long> map = new LinkedHashMap<>();

        List<?> list = currentSession.createQuery(
                "select concat(doc.lastName, ' ', doc.firstName, ' ', doc.patronymic), count(r.recipeId) " +
                        "from Recipe r right join r.doctor doc " +
                        "group by doc.doctorId").getResultList();
        for (Object obj : list) {
            map.put(((Object[]) obj)[0].toString(),
                    Long.valueOf((((Object[])obj)[1]).toString()));
        }
        return map;
    }

    @Override
    public List<Recipe> filterByDescription(String description) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Recipe> theQuery =
                currentSession.createQuery(
                        "from Recipe where description like concat('%', :description, '%')", Recipe.class);
        theQuery.setParameter("description", description);
        return theQuery.getResultList();
    }

    @Override
    public List<Recipe> filterByPatientId(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Recipe> theQuery =
                currentSession.createQuery("from Recipe where patientId = :patientId", Recipe.class);
        theQuery.setParameter("patientId", theId);
        return theQuery.getResultList();
    }

    @Override
    public List<Recipe> filterByDoctorId(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Recipe> theQuery =
                currentSession.createQuery("from Recipe where doctorId = :doctorId", Recipe.class);
        theQuery.setParameter("doctorId", theId);
        return theQuery.getResultList();
    }

    @Override
    public List<Recipe> filterByPriorityId(Long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Recipe> theQuery =
                currentSession.createQuery("from Recipe where priorityId = :priorityId", Recipe.class);
        theQuery.setParameter("priorityId", theId);
        return theQuery.getResultList();
    }
}
