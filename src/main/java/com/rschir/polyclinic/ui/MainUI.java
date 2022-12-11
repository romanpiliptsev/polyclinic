//package com.haulmont.testtask.ui;
//
//import com.haulmont.testtask.ui.utils.Views;
//import com.haulmont.testtask.ui.views.MainView;
//import com.haulmont.testtask.ui.views.patientviews.PatientView;
//import com.haulmont.testtask.ui.views.doctorviews.DoctorView;
//import com.haulmont.testtask.ui.views.recipeviews.RecipeView;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.navigator.Navigator;
//import com.vaadin.navigator.PushStateNavigation;
//import com.vaadin.navigator.ViewDisplay;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.*;
//import com.vaadin.ui.themes.ValoTheme;
//
//import javax.servlet.annotation.WebServlet;
//
//@Theme(ValoTheme.THEME_NAME)
//@PushStateNavigation
//public class MainUI extends UI {
//
//    @WebServlet(value = "/*", asyncSupported = true)
//    @VaadinServletConfiguration(productionMode = true, ui = MainUI.class)
//    public static class Servlet extends VaadinServlet {
//    }
//
//    @Override
//    protected void init(VaadinRequest request) {
//
//        getPage().setTitle("Поликлиника");
//
//        HorizontalLayout mainLayout = new HorizontalLayout();
//        mainLayout.setSizeFull();
//        mainLayout.setMargin(false);
//        mainLayout.setSpacing(false);
//
//        //creating side menu for navigating between views
//        VerticalLayout sideMenu = new VerticalLayout();
//        sideMenu.setWidthFull();
//        sideMenu.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        sideMenu.setHeightFull();
//        sideMenu.setMargin(false);
//        sideMenu.setSpacing(true);
//
//        Button patientButton =
//                new Button("Пациенты", clickEvent -> getUI().getNavigator().navigateTo(Views.Patients.name()));
//        patientButton.setWidth("150px");
//
//        Button doctorButton =
//                new Button("Доктора", clickEvent -> getUI().getNavigator().navigateTo(Views.Doctors.name()));
//        doctorButton.setWidth("150px");
//
//        Button receiptButton =
//                new Button("Рецепты", clickEvent -> getUI().getNavigator().navigateTo(Views.Recipes.name()));
//        receiptButton.setWidth("150px");
//
//        sideMenu.addComponents(patientButton, doctorButton, receiptButton);
//
//        Panel sideMenuPanel = new Panel();
//        sideMenuPanel.setWidth("200px");
//        sideMenuPanel.setHeight("50%");
//        sideMenuPanel.setContent(sideMenu);
//
//        VerticalLayout viewsLayout = new VerticalLayout();
//        viewsLayout.setSizeFull();
//        viewsLayout.setMargin(false);
//        viewsLayout.setSpacing(true);
//
//        mainLayout.addComponents(sideMenuPanel,viewsLayout);
//        mainLayout.setComponentAlignment(sideMenuPanel,Alignment.MIDDLE_CENTER);
//        mainLayout.setExpandRatio(viewsLayout, 1f);
//
//        //setting up navigator
//        ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(viewsLayout);
//        Navigator navigator = new Navigator(this, viewDisplay);
//        navigator.addView("", new MainView());
//        navigator.addView(Views.Doctors.name(), new DoctorView());
//        navigator.addView(Views.Patients.name(), new PatientView());
//        navigator.addView(Views.Recipes.name(), new RecipeView());
//
//        setContent(mainLayout);
//
//    }
//}