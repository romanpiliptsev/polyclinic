package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.dbservice.dao.interfaces.PriorityDao;
import com.rschir.polyclinic.dbservice.entities.RecipePriority;
import com.rschir.polyclinic.dbservice.services.interfaces.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PriorityServiceImpl implements PriorityService {
    @Autowired
    private PriorityDao priorityDao;

    @Override
    @Transactional
    public void save(RecipePriority recipePriority) {
        priorityDao.save(recipePriority);
    }

    @Override
    @Transactional
    public RecipePriority getById(Long id) {
        return priorityDao.getById(id);
    }

    @Override
    @Transactional
    public List<RecipePriority> getAll() {
        return priorityDao.getAll();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        priorityDao.remove(id);
    }
}
