package com.czy.fx.test.test65_TableView.yiNanPing.model;

import com.czy.fx.test.test65_TableView.yiNanPing.Checkbox;
import com.czy.util.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class Person {
    @JsonIgnore
    private final SimpleStringProperty name = new SimpleStringProperty();
    @JsonIgnore
    private final SimpleStringProperty address= new SimpleStringProperty();
    @JsonIgnore
    private final SimpleStringProperty telephoneNumber= new SimpleStringProperty();
    @JsonIgnore
    private final SimpleStringProperty email= new SimpleStringProperty();
    @JsonIgnore
    public Checkbox cb = new Checkbox();

    public Person() {
    }
    public Person(String name, String address, String telephoneNumber, String email) {
        setName(name);
        setAddress(address);
        setTelephoneNumber(telephoneNumber);
        setEmail(email);
    }

    public String getName() {
        return name.get();
    }
    @JsonProperty
    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }
    @JsonProperty
    public void setAddress(String address) {
        this.address.set(address);
        ;
    }

    public String getTelephoneNumber() {
        return telephoneNumber.get();
    }
    @JsonProperty
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(telephoneNumber);
        ;
    }
    public String getEmail() {
        return email.get();
    }
    @JsonProperty
    public void setEmail(String email) {
        this.email.set(email);
    }

    public String toString() {
        return JsonUtil.model2Str(this);
    }
}
