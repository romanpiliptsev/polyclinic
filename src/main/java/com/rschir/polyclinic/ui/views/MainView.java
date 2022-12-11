//package com.haulmont.testtask.ui.views;

//
//import com.haulmont.testtask.config.AppConfig;
//import com.haulmont.testtask.dbservice.services.DoctorServiceImpl;
//import com.haulmont.testtask.dbservice.services.PatientServiceImpl;
//import com.haulmont.testtask.dbservice.services.PriorityServiceImpl;
//import com.haulmont.testtask.dbservice.services.RecipeServiceImpl;
//import com.haulmont.testtask.dbservice.services.interfaces.DoctorService;
//import com.haulmont.testtask.dbservice.services.interfaces.PatientService;
//import com.haulmont.testtask.dbservice.services.interfaces.PriorityService;
//import com.haulmont.testtask.dbservice.services.interfaces.RecipeService;
//
//import com.vaadin.navigator.View;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.VerticalLayout;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//public class MainView { // extends VerticalLayout implements View {
//
//    private static DoctorService doctorService;
//    private static PatientService patientService;
//    private static RecipeService recipeService;
//    private static PriorityService priorityService;
//
//    public MainView() {
//
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        doctorService = context.getBean("doctorServiceImpl", DoctorServiceImpl.class);
//        patientService = context.getBean("patientServiceImpl", PatientServiceImpl.class);
//        recipeService = context.getBean("recipeServiceImpl", RecipeServiceImpl.class);
//        priorityService = context.getBean("priorityServiceImpl", PriorityServiceImpl.class);
//
//        setSizeFull();
//        setMargin(false);
//        setSpacing(false);
//
//        HorizontalLayout headerLayout = new HorizontalLayout();
//        headerLayout.setWidthFull();
//        headerLayout.setHeight("50px");
//        headerLayout.setMargin(true);
//        headerLayout.setSpacing(true);
//
//        Label label = new Label("Добро пожаловать в поликлинику");
//        headerLayout.addComponent(label);
//        headerLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
//        addComponent(headerLayout);
//
//    }
//
//    public static DoctorService getDoctorService() {
//        return doctorService;
//    }
//
//    public static PatientService getPatientService() {
//        return patientService;
//    }
//
//    public static RecipeService getRecipeService() {
//        return recipeService;
//    }
//
//    public static PriorityService getPriorityService() {
//        return priorityService;
//    }
//}
