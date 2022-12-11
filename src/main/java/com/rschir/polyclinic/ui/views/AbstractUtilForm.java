//package com.haulmont.testtask.ui.views;
//
//import com.haulmont.testtask.ui.utils.Operations;
//import com.vaadin.ui.*;
//
///**
// * Abstract edit/add form class with control buttons
// * and some util methods
// */
//public abstract class AbstractUtilForm<T> extends FormLayout {
//
//    protected Button saveBt;
//    protected Button closeBt = new Button("Отмена");
//    protected T domainObject;
//    protected Operations operation;
//
//    public AbstractUtilForm(Operations operation, T domainObject) {
//        this.operation = operation;
//        this.domainObject = domainObject;
//        switch (operation) {
//            case edit: {
//                saveBt = new Button("Сохранить");
//                break;
//            }
//            case add: {
//                saveBt = new Button("Добавить");
//                break;
//            }
//        }
//
//        HorizontalLayout buttonsLayout = new HorizontalLayout(saveBt, closeBt);
//        buttonsLayout.setSpacing(true);
//        addComponent(buttonsLayout);
//        setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
//    }
//
//    protected abstract void setupBtListeners();
//
//    protected abstract void setupBinders();
//
//
//    public static TextField createTextField(String caption, int maxLength) {
//        TextField textField = new TextField(caption);
//        textField.setMaxLength(maxLength);
//        textField.setWidth("100%");
//        //textField.setRequiredIndicatorVisible(true);
//        return textField;
//    }
//
//    public Operations getOperation() {
//        return operation;
//    }
//
//    public void setOperation(Operations operation) {
//        this.operation = operation;
//    }
//}
