package com.rschir.polyclinic.dbservice.dao.interfaces;

import com.rschir.polyclinic.dbservice.entities.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeDao extends Dao<Recipe> {
    Map<String, Long> getCountOfRecipesByDoctor();
    List<Recipe> filterByDescription(String description);
    List<Recipe> filterByPatientId(Long patientId);
    List<Recipe> filterByPriorityId(Long priorityId);
    List<Recipe> filterByDoctorId(Long doctorId);
}
