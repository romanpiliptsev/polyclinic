//package com.haulmont.testtask.ui.views.doctorviews;
//
//import com.haulmont.testtask.ui.views.MainView;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
//
//import java.util.Map;
//import java.util.Map.Entry;
//
///**
// * DoctorStatsWindow class represents a grid with amount of recipes given by every doctor
// */
//public class DoctorStatsWindow extends Window {
//
//    private final Grid<Map.Entry<String, Long>> doctorStatsGrid = new Grid<>("Количество рецептов, выписанных докторами");
//
//    public DoctorStatsWindow() {
//        VerticalLayout layout = new VerticalLayout();
//        layout.setSizeFull();
//
//        setGrid();
//        layout.addComponent(doctorStatsGrid);
//
//
//        setWidth("600px");
//        setHeight("400px");
//        setModal(true);
//        setContent(layout);
//    }
//
//    private void setGrid() {
//        doctorStatsGrid.setSizeFull();
//        doctorStatsGrid.addColumn(Entry::getKey).setCaption("Доктор");
//        doctorStatsGrid.addColumn(Entry::getValue).setCaption("Количество рецептов");
//        doctorStatsGrid.setItems(MainView.getRecipeService().getCountOfRecipesByDoctor().entrySet());
//    }
//}
