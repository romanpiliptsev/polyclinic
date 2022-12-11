//package com.haulmont.testtask.ui.views.patientviews;
//
//import com.haulmont.testtask.dbservice.entities.Patient;
//import com.haulmont.testtask.dbservice.services.interfaces.PatientService;
//import com.haulmont.testtask.ui.utils.Operations;
//import com.haulmont.testtask.ui.views.MainView;
//import com.vaadin.navigator.View;
//import com.vaadin.ui.*;
//
///**
// * PatientView class represents a grid of patients data
// */
//public class PatientView extends VerticalLayout implements View {
//
//    private PatientService patientService;
//
//    private final Grid<Patient> patientGrid = new Grid<>("Пациенты");
//    private final Button editBt = new Button("Редактировать");
//    private final Button addBt = new Button("Добавить");
//    private final Button delBt = new Button("Удалить");
//
//    public PatientView() {
//
//        patientService = MainView.getPatientService();
//
//        setGrid();
//        updateGrid();
//
//        HorizontalLayout buttonsLayout = new HorizontalLayout();
//        buttonsLayout.addComponents(addBt, editBt, delBt);
//        setCrudButtons();
//
//        VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(false);
//        layout.addComponent(patientGrid);
//        layout.addComponent(buttonsLayout);
//
//        addComponent(layout);
//        setSpacing(false);
//    }
//
//    private void setGrid() {
//        patientGrid.addColumn(Patient::getLastName).setCaption("Фамилия");
//        patientGrid.addColumn(Patient::getFirstName).setCaption("Имя");
//        patientGrid.addColumn(Patient::getPatronymic).setCaption("Отчество");
//        patientGrid.addColumn(Patient::getPhoneNumber).setCaption("Телефон");
//        patientGrid.setSizeFull();
//        patientGrid.addSelectionListener(valueChangeEvent -> {
//            if (!patientGrid.asSingleSelect().isEmpty()) {
//                editBt.setEnabled(true);
//                delBt.setEnabled(true);
//            } else {
//                editBt.setEnabled(false);
//                delBt.setEnabled(false);
//            }
//        });
//    }
//
//    private void updateGrid() {
//        patientGrid.setItems(patientService.getAll());
//    }
//
//    private void setCrudButtons() {
//        editBt.setEnabled(false);
//        delBt.setEnabled(false);
//        addBt.addClickListener(click -> {
//            Window patientWindow = new PatientFormWindow(Operations.add, new Patient());
//            patientWindow.addCloseListener(closeEvent -> {
//                updateGrid();
//            });
//            getUI().addWindow(patientWindow);
//        });
//        editBt.addClickListener(click -> {
//            Patient patient = patientGrid.asSingleSelect().getValue();
//            Window patientWindow = new PatientFormWindow(Operations.edit, patient);
//            patientWindow.addCloseListener(closeEvent -> {
//                updateGrid();
//            });
//            getUI().addWindow(patientWindow);
//        });
//        delBt.addClickListener(click -> {
//            Patient patient = patientGrid.asSingleSelect().getValue();
//            patientService.remove(patient.getPatientId());
//            updateGrid();
//        });
//    }
//}
