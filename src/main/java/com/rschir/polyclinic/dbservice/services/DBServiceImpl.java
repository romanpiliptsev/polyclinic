package com.rschir.polyclinic.dbservice.services;

import com.rschir.polyclinic.dbservice.services.interfaces.DBService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBServiceImpl implements DBService {

    public static Connection getHSQLConnection()  {
        Connection connection = null;
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String driver = resource.getString("db.driver");
        String url = resource.getString("db.url");
        String username = resource.getString("db.user");
        String password = resource.getString("db.password");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver hsql is not found");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Can't connect to database:" + url);
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void createDB() {
        runSqlScript("db/createDB.sql");
    }

    @Override
    public void populateDB() {
        runSqlScript("db/popDB.sql");
    }

    @Override
    public void cleanDB() {
        runSqlScript("db/cleanDB.sql");
    }

    @Override
    public void runSqlScript(String scriptPath) {
        File file = new File(scriptPath);
        String createQuery = null;
        try {
            createQuery = FileUtils.readFileToString(file,"utf-8");
        } catch (IOException e) {
            System.out.println("Can't read script file: " + scriptPath);
            e.printStackTrace();
        }
        try {
            getHSQLConnection().createStatement().execute(createQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
