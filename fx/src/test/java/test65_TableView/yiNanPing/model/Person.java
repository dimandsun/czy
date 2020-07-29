package test65_TableView.yiNanPing.model;

import com.czy.util.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import test65_TableView.yiNanPing.Checkbox;

import java.io.*;
import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class Person {
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();
    private final SimpleStringProperty addressProperty= new SimpleStringProperty();
    private final SimpleStringProperty telephoneNumberProperty= new SimpleStringProperty();
    private final SimpleStringProperty emailProperty= new SimpleStringProperty();
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
        return nameProperty.get();
    }

    public void setName(String name) {
        this.nameProperty.set(name);
    }

    public String getAddress() {
        return addressProperty.get();
    }

    public void setAddress(String address) {
        this.addressProperty.set(address);
        ;
    }

    public String getTelephoneNumber() {
        return telephoneNumberProperty.get();
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumberProperty.set(telephoneNumber);
        ;
    }

    public String getEmail() {
        return emailProperty.get();
    }

    public void setEmail(String email) {
        this.emailProperty.set(email);
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public SimpleStringProperty namePropertyProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }

    public String getAddressProperty() {
        return addressProperty.get();
    }

    public SimpleStringProperty addressPropertyProperty() {
        return addressProperty;
    }

    public void setAddressProperty(String addressProperty) {
        this.addressProperty.set(addressProperty);
    }

    public String getTelephoneNumberProperty() {
        return telephoneNumberProperty.get();
    }

    public SimpleStringProperty telephoneNumberPropertyProperty() {
        return telephoneNumberProperty;
    }

    public void setTelephoneNumberProperty(String telephoneNumberProperty) {
        this.telephoneNumberProperty.set(telephoneNumberProperty);
    }

    public String getEmailProperty() {
        return emailProperty.get();
    }

    public SimpleStringProperty emailPropertyProperty() {
        return emailProperty;
    }

    public void setEmailProperty(String emailProperty) {
        this.emailProperty.set(emailProperty);
    }

    public String toString() {
        return JsonUtil.model2Str(this);
    }

    public void writeToFile(File f) throws ClassNotFoundException, IOException {

        Person person = new Person(nameProperty.get(), addressProperty.get(), telephoneNumberProperty.get(), emailProperty.get());

        ArrayList<Person> list = new ArrayList<>();// 用来存文件之前的对象


        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                list.add((Person) input.readObject());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {// 捕捉到异常时，原有对象已全部存入list中

        } finally {
            // 序列化
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f))) {
                // 将文件中原有的对象重新存入
                for (Person p : list) {
                    output.writeObject(p);
                }
                output.writeObject(person);// 将新对象存入

            }

        }

    }
}
