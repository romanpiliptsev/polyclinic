package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.dbservice.dao.interfaces.RecipeDao;
import com.rschir.polyclinic.dbservice.entities.Recipe;
import com.rschir.polyclinic.dbservice.services.interfaces.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDao hibernateRecipeDao;

    @Override
    @Transactional
    public void save(Recipe recipe) {
        hibernateRecipeDao.save(recipe);
    }

    @Override
    @Transactional
    public Recipe getById(Long id) {
        return hibernateRecipeDao.getById(id);
    }

    @Override
    @Transactional
    public List<Recipe> getAll() {
        return hibernateRecipeDao.getAll();
    }


    @Override
    @Transactional
    public void remove(Long id) {
        hibernateRecipeDao.remove(id);
    }

    @Override
    @Transactional
    public Map<String,Long> getCountOfRecipesByDoctor() {
        return hibernateRecipeDao.getCountOfRecipesByDoctor();
    }

    @Override
    @Transactional
    public List<Recipe> filterByDescription(String description) {
        return hibernateRecipeDao.filterByDescription(description);
    }

    @Override
    @Transactional
    public List<Recipe> filterByPatientId(Long patientId) {
        return hibernateRecipeDao.filterByPatientId(patientId);
    }

    @Override
    @Transactional
    public List<Recipe> filterByDoctorId(Long doctorId) {
        return hibernateRecipeDao.filterByDoctorId(doctorId);
    }

    @Override
    @Transactional
    public List<Recipe> filterByPriorityId(Long priorityId) {
        return hibernateRecipeDao.filterByPriorityId(priorityId);
    }
}
