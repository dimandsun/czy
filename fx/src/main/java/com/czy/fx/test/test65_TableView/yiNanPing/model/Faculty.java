package com.czy.fx.test.test65_TableView.yiNanPing.model;

import com.czy.fx.test.test65_TableView.yiNanPing.Checkbox;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class Faculty extends Employee {
    private static final long serialVersionUID = 1L;

    private final SimpleStringProperty officeHours;

    private final SimpleStringProperty level;
    public Checkbox cb = new Checkbox();

    public Faculty(String officeHours, String level, String office, String wages, String dateOfAppointment,
                   String name, String address, String telephoneNumber, String email) {
        super(office, wages, dateOfAppointment, name, address, telephoneNumber, email);
        this.officeHours = new SimpleStringProperty(officeHours);
        this.level = new SimpleStringProperty(level);
    }

    public String getOfficeHours() {
        return officeHours.get();
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours.set(officeHours);
    }

    public String getLevel() {
        return level.get();
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public String toString() {
        return "Faculty " + this.getName();
    }
}