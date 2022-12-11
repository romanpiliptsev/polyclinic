package com.rschir.polyclinic.dbservice.services.interfaces;

import com.rschir.polyclinic.dbservice.entities.Recipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface RecipeService extends Service<Recipe> {

    @Transactional
    Map<String,Long> getCountOfRecipesByDoctor();

    @Transactional
    List<Recipe> filterByDescription(String description);

    @Transactional
    List<Recipe> filterByPatientId(Long patientId);

    @Transactional
    List<Recipe> filterByPriorityId(Long priorityId);

    @Transactional
    List<Recipe> filterByDoctorId(Long doctorId);
}
