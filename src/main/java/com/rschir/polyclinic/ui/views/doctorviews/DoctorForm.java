//package com.haulmont.testtask.ui.views.doctorviews;
//
//import com.haulmont.testtask.dbservice.entities.Doctor;
//import com.haulmont.testtask.ui.utils.Operations;
//import com.haulmont.testtask.ui.views.AbstractUtilForm;
//import com.haulmont.testtask.ui.views.MainView;
//
//import com.vaadin.data.Binder;
//import com.vaadin.data.ValidationException;
//import com.vaadin.navigator.View;
//import com.vaadin.ui.*;
//
///**
// * Form for editing or adding doctors
// */
//public class DoctorForm extends AbstractUtilForm<Doctor> implements View {
//    private TextField firstNameText;
//    private TextField surnameText;
//    private TextField patronymicText;
//    private TextField specializationText;
//
//    private Binder<Doctor> binder = new Binder<>(Doctor.class);
//
//    public DoctorForm(Operations operation, Doctor doctor) {
//
//        super(operation, doctor);
//
//        setMargin(true);
//        setSpacing(true);
//
//        //initializing fields
//        surnameText = createTextField("Фамилия:", 255);
//        firstNameText = createTextField("Имя:", 255);
//        patronymicText = createTextField("Отчество:", 255);
//        specializationText = createTextField("Специализация:", 255);
//
//        //binders + validation
//        setupBinders();
//
//        addComponentAsFirst(specializationText);
//        addComponentAsFirst(patronymicText);
//        addComponentAsFirst(firstNameText);
//        addComponentAsFirst(surnameText);
//
//        setupBtListeners();
//
//        if (operation == Operations.edit) {
//            System.out.println(domainObject);
//            binder.readBean(domainObject);
//        }
//    }
//
//    @Override
//    protected void setupBinders() {
//        binder.forField(firstNameText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите имя")
//                .asRequired()
//                .bind(Doctor::getFirstName, Doctor::setFirstName);
//        binder.forField(surnameText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите фамилию")
//                .asRequired()
//                .bind(Doctor::getLastName, Doctor::setLastName);
//        binder.forField(patronymicText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите отчество")
//                .asRequired()
//                .bind(Doctor::getPatronymic, Doctor::setPatronymic);
//        binder.forField(specializationText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите специализацию")
//                .asRequired()
//                .bind(Doctor::getSpecialization, Doctor::setSpecialization);
//    }
//
//    @Override
//    protected void setupBtListeners() {
//        saveBt.addClickListener(e -> {
//            if (binder.validate().isOk()) {
//                if (operation == Operations.edit) {
//                    try {
//                        binder.writeBean(domainObject);
//                    } catch (ValidationException ex) {
//                        ex.printStackTrace();
//                    }
//                    MainView.getDoctorService().save(domainObject);
//                    findAncestor(Window.class).close();
//                } else if (operation == Operations.add) {
//                    try {
//                        binder.writeBean(domainObject);
//                    } catch (ValidationException ex) {
//                        ex.printStackTrace();
//                    }
//                    MainView.getDoctorService().save(domainObject);
//                    findAncestor(Window.class).close();
//                }
//            }
//        });
//
//        closeBt.addClickListener(e -> {
//            findAncestor(Window.class).close();
//        });
//    }
//}
