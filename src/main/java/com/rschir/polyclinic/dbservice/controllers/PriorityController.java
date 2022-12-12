package com.rschir.polyclinic.dbservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rschir.polyclinic.dbservice.entities.Patient;
import com.rschir.polyclinic.dbservice.entities.RecipePriority;
import com.rschir.polyclinic.dbservice.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PriorityController {
    @Autowired
    private PriorityService priorityService;



    @RequestMapping(value = "/stuff/recipe_priorities", method = RequestMethod.GET)
    public List<RecipePriority> recipePriorities() {
        return priorityService.getAll();
    }
}
