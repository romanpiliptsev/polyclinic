package com.rschir.polyclinic;

import com.rschir.polyclinic.dbservice.services.DBServiceImpl;
import com.rschir.polyclinic.dbservice.services.interfaces.DBService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class PolyclinicApplication {
    public static void main(String[] args) {
        DBService db = new DBServiceImpl();
        DBServiceImpl.getHSQLConnection();
//        db.cleanDB();
//        db.createDB();
//        db.populateDB();

        SpringApplication.run(PolyclinicApplication.class, args);
    }
}
