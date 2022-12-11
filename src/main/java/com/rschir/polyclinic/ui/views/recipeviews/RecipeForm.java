//package com.haulmont.testtask.ui.views.recipeviews;
//
//import com.haulmont.testtask.dbservice.entities.Doctor;
//import com.haulmont.testtask.dbservice.entities.Patient;
//import com.haulmont.testtask.dbservice.entities.Recipe;
//import com.haulmont.testtask.dbservice.entities.RecipePriority;
//import com.haulmont.testtask.ui.utils.Operations;
//import com.haulmont.testtask.ui.views.AbstractUtilForm;
//import com.haulmont.testtask.ui.views.MainView;
//import com.vaadin.data.Binder;
//import com.vaadin.data.ValidationException;
//import com.vaadin.data.converter.LocalDateToDateConverter;
//import com.vaadin.data.converter.StringToIntegerConverter;
//import com.vaadin.navigator.View;
//import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.DateField;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.Window;
//
//import java.util.List;
//import java.util.Objects;
//
//
///**
// * Form for editing or adding recipes
// */
//public class RecipeForm extends AbstractUtilForm<Recipe> implements View {
//
//    private TextField descriptionText;
//    private ComboBox<Patient> patientCB;
//    private ComboBox<Doctor> doctorCB;
//    private DateField creationDateField;
//    private TextField validityText;
//    private ComboBox<RecipePriority> priorityCB;
//
//    private Binder<Recipe> binder = new Binder<>(Recipe.class);
//
//    public RecipeForm(Operations operation, Recipe recipe) {
//
//        super(operation, recipe);
//
//        setMargin(true);
//        setSpacing(true);
//
//        //initializing fields
//        descriptionText = createTextField("Имя:", 255);
//
//        patientCB = new ComboBox<>("Пациент");
//        patientCB.setPlaceholder("Выберите пациента");
//        patientCB.setWidth("100%");
//        RecipeView.populatePatientsCB(patientCB);
//
//        doctorCB = new ComboBox<>("Доктор");
//        doctorCB.setPlaceholder("Выберите доктора");
//        doctorCB.setWidth("100%");
//        List<Doctor> doctors = MainView.getDoctorService().getAll();
//        doctorCB.setItems(doctors);
//        doctorCB.setItemCaptionGenerator(doctor ->
//                doctor.getDoctorId() + " " + doctor.getLastName() + " " + doctor.getFirstName());
//
//
//        creationDateField = new DateField("Дата создания");
//        creationDateField.setDateFormat("dd.MM.yyyy");
//        creationDateField.setPlaceholder("дд.мм.гггг");
//        creationDateField.setWidth("100%");
//
//        validityText = createTextField("Срок действия:", 10);
//
//        priorityCB = new ComboBox<>("Приоритет");
//        priorityCB.setTextInputAllowed(false);
//        priorityCB.setPlaceholder("Выберите приоритет");
//        priorityCB.setWidth("100%");
//        priorityCB.setItems(MainView.getPriorityService().getAll());
//
//
//        //binders + validation
//        setupBinders();
//
//
//        addComponentAsFirst(priorityCB);
//        addComponentAsFirst(validityText);
//        addComponentAsFirst(creationDateField);
//        addComponentAsFirst(doctorCB);
//        addComponentAsFirst(patientCB);
//        addComponentAsFirst(doctorCB);
//        addComponentAsFirst(descriptionText);
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
//        binder.forField(descriptionText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите описание")
//                .asRequired()
//                .bind(Recipe::getDescription, Recipe::setDescription);
//        binder.forField(patientCB)
//                .withValidator(Objects::nonNull, "Выберите пациента")
//                .asRequired()
//                .bind(Recipe::getPatient, Recipe::setPatient);
//        binder.forField(doctorCB)
//                .withValidator(Objects::nonNull, "Выберите доктора")
//                .asRequired()
//                .bind(Recipe::getDoctor, Recipe::setDoctor);
//        binder.forField(creationDateField)
//                .withConverter(new LocalDateToDateConverter())
//                .withValidator(Objects::nonNull, "Выберите дату создания")
//                .asRequired()
//                .bind(Recipe::getCreationDate, Recipe::setCreationDate);
//        binder.forField(validityText)
//                .withValidator(string -> string != null && !string.isEmpty(), "Введите срок действия")
//                .asRequired()
//                .withConverter(new StringToIntegerConverter("Введите количество дней"))
//                .withValidator(integer -> integer >= 0 && integer < 100000, "Некорректное число дней")
//                .bind(Recipe::getValidity, Recipe::setValidity);
//        binder.forField(priorityCB)
//                .withValidator(Objects::nonNull, "Выберите приоритет")
//                .asRequired()
//                .bind(Recipe::getRecipePriority, Recipe::setRecipePriority);
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
//                    MainView.getRecipeService().save(domainObject);
//                    findAncestor(Window.class).close();
//                } else if (operation == Operations.add) {
//                    try {
//                        binder.writeBean(domainObject);
//                    } catch (ValidationException ex) {
//                        ex.printStackTrace();
//                    }
//                    MainView.getRecipeService().save(domainObject);
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
