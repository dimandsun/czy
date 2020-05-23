package com.czy.fx.test.test52_binding;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author chenzy
 * @description
 * @since 2020-05-23
 */
public class Name {
    private SimpleStringProperty name=new SimpleStringProperty();

    public Name() {
    }

    public Name(String name) {
        setName(name);
    }

    public SimpleStringProperty nameProperty() {
        System.out.println("nameProperty:");
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
