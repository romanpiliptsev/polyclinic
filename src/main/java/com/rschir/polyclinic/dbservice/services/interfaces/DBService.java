package com.rschir.polyclinic.dbservice.services.interfaces;

public interface DBService {
    void createDB();
    void populateDB();
    void cleanDB();
    void runSqlScript(String scriptPath);
}
