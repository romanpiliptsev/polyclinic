//package com.haulmont.testtask.ui.views.patientviews;
//
//import com.haulmont.testtask.dbservice.entities.Patient;
//import com.haulmont.testtask.ui.utils.Operations;
//import com.haulmont.testtask.ui.views.AbstractUtilForm;
//import com.haulmont.testtask.ui.views.MainView;
//import com.vaadin.data.Binder;
//import com.vaadin.data.ValidationException;
//import com.vaadin.navigator.View;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.Window;
//
///**
// * Form for editing or adding patients
// */
//public class PatientForm extends AbstractUtilForm<Patient> implements View {
//    private TextField firstNameText;
//    private TextField surnameText;
//    private TextField patronymicText;
//    private TextField phoneNumberText;
//
//    private Binder<Patient> binder = new Binder<>(Patient.class);
//
//    public PatientForm(Operations operation, Patient patient) {
//
//        super(operation, patient);
//
//        setMargin(true);
//        setSpacing(true);
//
//        //initializing fields
//        surnameText = createTextField("Фамилия:", 255);
//        firstNameText = createTextField("Имя:", 255);
//        patronymicText = createTextField("Отчество:", 255);
//        phoneNumberText = createTextField("Телефон:", 15);
//
//        //binders + validation
//        setupBinders();
//
//        addComponentAsFirst(phoneNumberText);
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
//                .bind(Patient::getFirstName, Patient::setFirstName);
//        binder.forField(surnameText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите фамилию")
//                .asRequired()
//                .bind(Patient::getLastName, Patient::setLastName);
//        binder.forField(patronymicText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите отчество")
//                .asRequired()
//                .bind(Patient::getPatronymic, Patient::setPatronymic);
//        binder.forField(phoneNumberText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите телефон")
//                .asRequired()
//                .bind(Patient::getPhoneNumber, Patient::setPhoneNumber);
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
//                    MainView.getPatientService().save(domainObject);
//                    findAncestor(Window.class).close();
//                } else if (operation == Operations.add) {
//                    try {
//                        binder.writeBean(domainObject);
//                    } catch (ValidationException ex) {
//                        ex.printStackTrace();
//                    }
//                    MainView.getPatientService().save(domainObject);
//                    findAncestor(Window.class).close();
//                }
//            }
//        });
//
//        closeBt.addClickListener(e -> {
//            findAncestor(Window.class).close();
//        });
//    }
//
//
//}
