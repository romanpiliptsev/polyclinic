//package com.haulmont.testtask.ui.views.recipeviews;
//
//import com.haulmont.testtask.dbservice.entities.Patient;
//import com.haulmont.testtask.dbservice.entities.Recipe;
//import com.haulmont.testtask.dbservice.entities.RecipePriority;
//import com.haulmont.testtask.dbservice.services.interfaces.RecipeService;
//import com.haulmont.testtask.ui.utils.Operations;
//import com.haulmont.testtask.ui.views.MainView;
//import com.vaadin.navigator.View;
//import com.vaadin.ui.*;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * RecipeView class represents a grid of recipes data
// */
//public class RecipeView extends VerticalLayout implements View {
//
//    private RecipeService recipeService;
//
//    private final Grid<Recipe> recipeGrid = new Grid<>("Выписанные рецепты");
//    private final Button editBt = new Button("Редактировать");
//    private final Button addBt = new Button("Добавить");
//    private final Button delBt = new Button("Удалить");
//    private final TextField descriptionText = new TextField();
//    private final ComboBox<Patient> patientCB = new ComboBox<>();
//    private final ComboBox<RecipePriority> priorityCB = new ComboBox<>();
//    private final Button applyFilterBt = new Button("Применить");
//    private final Button clearFilterBt = new Button("Убрать");
//
//    public RecipeView() {
//        recipeService = MainView.getRecipeService();
//
//        Panel filterPanel = new Panel();
//        filterPanel.setCaption("Фильтры");
//        HorizontalLayout filterLayout = new HorizontalLayout();
//        filterLayout.setMargin(true);
//        filterLayout.setSpacing(true);
//        filterLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        filterLayout.addComponents(descriptionText, patientCB, priorityCB, applyFilterBt, clearFilterBt);
//        setFilters();
//        filterPanel.setContent(filterLayout);
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
//        layout.addComponent(filterPanel);
//        layout.addComponent(recipeGrid);
//        layout.addComponent(buttonsLayout);
//
//        addComponent(layout);
//        setSpacing(false);
//    }
//
//    private void setGrid() {
//        recipeGrid.addColumn(Recipe::getDescription).setCaption("Описание");
//        recipeGrid.addColumn(Recipe -> {
//            return Recipe.getPatient().getLastName() + " " +
//                    Recipe.getPatient().getFirstName();
//        }).setCaption("Пациент");
//        recipeGrid.addColumn(Recipe -> {
//            return Recipe.getDoctor().getLastName() + " " +
//                    Recipe.getDoctor().getFirstName();
//        }).setCaption("Доктор");
//        recipeGrid.addColumn(Recipe::getCreationDate).setCaption("Дата выписки");
//        recipeGrid.addColumn(Recipe -> {
//            int validity = Recipe.getValidity();
//            String days = null;
//            if (validity > 10 && validity < 15) {
//                days = " дней";
//            } else if (validity % 10 > 1 && validity % 10 < 5) {
//                days = " дня";
//            } else if (validity % 10 == 1) {
//                days = " день";
//            } else {
//                days = " дней";
//            }
//            return Recipe.getValidity() + days;
//        }).setCaption("Срок действия");
//        recipeGrid.addColumn(Recipe::getRecipePriority).setCaption("Приоритет");
//        recipeGrid.setSizeFull();
//        recipeGrid.addSelectionListener(valueChangeEvent -> {
//            if (!recipeGrid.asSingleSelect().isEmpty()) {
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
//        if (descriptionText.isEnabled()) {
//            populateGrid(recipeService.filterByDescription(descriptionText.getValue()));
//            return;
//        }
//        if (patientCB.isEnabled()) {
//            populateGrid(recipeService.filterByPatient(patientCB.getValue()));
//            return;
//        }
//        if (priorityCB.isEnabled()) {
//            populateGrid(recipeService.filterByPriority(priorityCB.getValue()));
//            return;
//        }
//        populateGrid(recipeService.getAll());
//    }
//
//    private void populateGrid(List<Recipe> items) {
//        recipeGrid.setItems(items);
//    }
//
//    private void setCrudButtons() {
//        editBt.setEnabled(false);
//        delBt.setEnabled(false);
//        addBt.addClickListener(click -> {
//            Window recipeWindow = new RecipeFormWindow(Operations.add, new Recipe());
//            recipeWindow.addCloseListener(closeEvent -> {
//                updateGrid();
//            });
//            getUI().addWindow(recipeWindow);
//        });
//        editBt.addClickListener(click -> {
//            Recipe recipe = recipeGrid.asSingleSelect().getValue();
//            Window recipeWindow = new RecipeFormWindow(Operations.edit, recipe);
//            recipeWindow.addCloseListener(closeEvent -> {
//                updateGrid();
//            });
//            getUI().addWindow(recipeWindow);
//        });
//        delBt.addClickListener(click -> {
//            Recipe recipe = recipeGrid.asSingleSelect().getValue();
//            recipeService.remove(recipe.getRecipeId());
//            updateGrid();
//        });
//    }
//
//    private void setFilters() {
//        //setting up filter fields
//        descriptionText.setWidth("250px");
//        descriptionText.setPlaceholder("Введите описание");
//
//        patientCB.setTextInputAllowed(true);
//        patientCB.setPlaceholder("Выберите пациента");
//        patientCB.setWidth("250px");
//        populatePatientsCB(patientCB);
//        patientCB.addFocusListener(focusEvent -> {
//            populatePatientsCB(patientCB);
//        });
//
//        priorityCB.setTextInputAllowed(false);
//        priorityCB.setPlaceholder("Выберите приоритет");
//        priorityCB.setWidth("250px");
//        priorityCB.setItems(MainView.getPriorityService().getAll());
//
//        //filter fields logic
//        applyFilterBt.setEnabled(false);
//        clearFilterBt.setEnabled(false);
//        descriptionText.addValueChangeListener(change -> {
//            if (!change.getValue().isEmpty()) {
//                patientCB.setEnabled(false);
//                priorityCB.setEnabled(false);
//                applyFilterBt.setEnabled(true);
//            } else {
//                patientCB.setEnabled(true);
//                priorityCB.setEnabled(true);
//                applyFilterBt.setEnabled(false);
//            }
//        });
//
//        patientCB.addValueChangeListener(change -> {
//            if (!Objects.isNull(change.getValue())) {
//                descriptionText.setEnabled(false);
//                priorityCB.setEnabled(false);
//                applyFilterBt.setEnabled(true);
//            } else {
//                descriptionText.setEnabled(true);
//                priorityCB.setEnabled(true);
//                applyFilterBt.setEnabled(false);
//            }
//        });
//
//        priorityCB.addValueChangeListener(change -> {
//            if (!Objects.isNull(change.getValue())) {
//                patientCB.setEnabled(false);
//                descriptionText.setEnabled(false);
//                applyFilterBt.setEnabled(true);
//            } else {
//                patientCB.setEnabled(true);
//                descriptionText.setEnabled(true);
//                applyFilterBt.setEnabled(false);
//            }
//        });
//
//        //filter buttons logic
//        applyFilterBt.addClickListener(click -> {
//            clearFilterBt.setEnabled(true);
//            updateGrid();
//        });
//
//        clearFilterBt.addClickListener(click -> {
//            clearFilterBt.setEnabled((false));
//            populateGrid(recipeService.getAll());
//        });
//    }
//
//    public static void populatePatientsCB(ComboBox<Patient> patientComboBox) {
//        List<Patient> patients = MainView.getPatientService().getAll();
//        patientComboBox.setItems(patients);
//        patientComboBox.setItemCaptionGenerator(patient ->
//                patient.getPatientId() + " " + patient.getLastName() + " " + patient.getFirstName());
//    }
//}
