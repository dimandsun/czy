package test53_Callback;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.util.Callback;

/**
 * @author chenzy
 *
 * @since 2020-05-23
 */
public class CallbackTest {

    public static void main(String[] args) {
        var stringProperty1=new SimpleStringProperty("afdsasfdafsdsassda");
        var stringProperty2=new SimpleStringProperty("2");
//
       var list = FXCollections.observableArrayList((Callback<SimpleStringProperty, Observable[]>) param -> new Observable[0]);
       list.add(stringProperty1);
        System.out.println(list.get(0).get());
    }
}
