package test65_TableView.yiNanPing.model;

import javafx.beans.property.SimpleStringProperty;
import test65_TableView.yiNanPing.Checkbox;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class Employee extends Person{
    private static final long serialVersionUID = 1L;

    private final SimpleStringProperty office;

    private final SimpleStringProperty wages;

    private SimpleStringProperty dateOfAppointment;

    public Checkbox cb = new Checkbox();

    public Employee(String office, String wages, String dateOfAppointment, String name, String address,
                    String telephoneNumber, String email) {
        super(name, address, telephoneNumber, email);
        this.office = new SimpleStringProperty(office);
        this.wages = new SimpleStringProperty(wages);
        this.dateOfAppointment = new SimpleStringProperty(dateOfAppointment);
    }

    public String getOffice() {
        return office.get();
    }

    public void setOffice(String office) {
        this.office.set(office);
    }

    public String getWages() {
        return wages.get();
    }

    public void setWages(String wages) {
        this.wages.set(wages);
    }

    public String getDateOfAppointment() {
        return dateOfAppointment.get();
    }

    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment.set(dateOfAppointment);
    }

    public String toString() {
        return "Employee " + this.getName();
    }
}
