package com.czy.fx.test.test52_binding;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author chenzy
 * @description
 * @since 2020-05-23
 */
public class Student {
    private SimpleObjectProperty<Name> studentName=new SimpleObjectProperty<>();

    public SimpleObjectProperty<Name> studentNameProperty() {
        System.out.println("studentNameProperty");
        return studentName;
    }
    public void setStudentName(Name name) {
        this.studentName.set(name);
    }

}
