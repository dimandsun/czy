package com.czy.fx.test.test52_binding;

import com.czy.fx.test.FXUtil;
import com.czy.user.model.User;
import com.czy.util.ObjectUtil;
import com.czy.util.model.StringMap;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Locale;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 *  单向绑定，双向绑定
 * @since 2020-05-21
 */
public class BindingTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button();
        var text1 = new TextField();
        var text2 = new TextField();
        var sliderBtn = new Button();
        var slider = new Slider();
        anchorPane.getChildren().addAll(btn, text1, text2, sliderBtn, slider);

        text1.textProperty().bindBidirectional(text2.textProperty(), new StringConverter<>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                println("getHeight:{},getLayoutY:{},getPrefHeight:{},getScaleY:{},getTranslateY:{}"
                        , text1.getHeight(), text1.getLayoutY(), text1.getPrefHeight(), text1.getScaleY(), text1.getTranslateY());
                return string + "-";
            }
        });
        var scale = new SimpleIntegerProperty(2);
        btn.prefWidthProperty().bind(anchorPane.widthProperty().divide(scale));
        sliderBtn.translateXProperty().bind(slider.valueProperty());
        FXUtil.setDefaultValue(primaryStage, anchorPane);

        AnchorPane.setTopAnchor(text1, btn.getHeight());
        AnchorPane.setTopAnchor(text2, btn.getHeight());
        AnchorPane.setLeftAnchor(text2, text1.getWidth());
        AnchorPane.setTopAnchor(sliderBtn, btn.getHeight() + btn.getHeight());
        AnchorPane.setTopAnchor(slider, btn.getHeight() + btn.getHeight() + sliderBtn.getHeight());
    }

    public static void main(String[] args) {
//        bind();

//        danBind();
//        shuangBind();
        testBindings();
    }

    /**
     * 绑定计算，实时绑定。使用值的时候才知道值是多少。
     */
    public static void bind() {
        var a = new SimpleIntegerProperty(1);
        var b = new SimpleIntegerProperty(2);
        /*b的值不会改变，返回的值为4*/
        var result = b.add(2);
        var result1 = b.add(a);

        println("a:{},b:{},\nb.add(2):{}\nb.add(a):{}", a.get(), b.get(), result.get(), result1.getValue());
        b.set(3);
        println("a:{},b:{},\nb.add(2):{}\nb.add(a):{}", a.get(), b.get(), result.get(), result1.getValue());

        /*判断大小*/
        a.greaterThan(b);
        a.lessThan(b);
        /*判断相等，在误差范围内*/
        a.isEqualTo(b, 1);
        /**/
        var bn = SimpleIntegerProperty.integerExpression(b);
        System.out.println(bn.getValue());
        /**/
        var bol = new SimpleBooleanProperty();
        new When(bol).then(new User()).otherwise(new User());
    }


    /**
     * 单向绑定x绑定到y上。x值随y值改变而改变
     * 绑定后x值被束缚。x.set()会报错，即无法直接给x设值，只能给y设值，y值改变了x值自然改变
     */
    public static void danBind() {
        var x = new SimpleIntegerProperty(1);
        var y = new SimpleIntegerProperty(2);
        x.bind(y);
        println("单向绑定:({},{})", x.get(), y.get());
        println("x是否被束缚{}", x.isBound());
        /*解绑*/
        x.unbind();
    }

    /**
     * 双向绑定:双方都不会被束缚，双方值同步
     */
    public static void shuangBind() {
        var x = new SimpleIntegerProperty(1);
        var y = new SimpleIntegerProperty(2);
        /*初始化时，以y为主*/
        x.bindBidirectional(y);
        println("双向绑定:({},{})", x.get(), y.get());
        x.set(3);
        y.set(4);
        println("双向绑定:({},{})", x.get(), y.get());
        /*解绑*/
        x.unbindBidirectional(y);
    }

    public static void listBind() {

        var userList = ObjectUtil.createList(User.class, "陈志源", "张三");
        var userObservableList = FXCollections.observableArrayList(userList);
        var userListProperty = new SimpleListProperty<>(userObservableList);

        var userList2 = ObjectUtil.createList(User.class, "陈晓云", "李四");
        var userObservableList2 = FXCollections.observableArrayList(userList);
        var userListProperty2 = new SimpleListProperty<>(userObservableList);
        /*单向绑定后，两个都是维护userList2，userListProperty将无法对userList做任何操作*/
        userListProperty.bind(userListProperty2);
        /*双向绑定后效果和单向绑定一样。*/
        userListProperty.bindBidirectional(userListProperty2);

        /*绑定内容后,userListProperty2添加的元素，userListProperty
            ，但是userListProperty添加的元素，不会加到userListProperty2
            简单说，就是列表2影响列表1，列表1不会影响列表2
            */
        userListProperty.bindContent(userListProperty2);

        /*真正的双向绑定内容：维护了两个列表，两个列表真正的双向绑定*/
        userListProperty.bindContentBidirectional(userObservableList2);
        System.out.println();


        /**/
        var userBinding = userListProperty.valueAt(0);
        var userBinding2 = userListProperty.valueAt(new SimpleIntegerProperty(0));
    }

    /**
     * 和list一样
     */
    public static void setBind() {
        var userObservableSet = FXCollections.observableSet(new User());
        var userSetProperty = new SimpleSetProperty<>(userObservableSet);

    }

    /**
     * 和list一样
     */
    public static void mapBind() {
        var userObservableMap = FXCollections.observableMap(new StringMap<>());
        var mapProperty = new SimpleMapProperty<>(userObservableMap);


        /**/
        var mapBinding = mapProperty.valueAt("key");
        var mapBinding2 = mapProperty.valueAt(new SimpleStringProperty("key"));
    }

    public static void testBindings() {
        var value = new SimpleIntegerProperty(10);
        var stringExpression = Bindings.concat("value=", value.asString(Locale.getDefault(), "%s"));
        System.out.println(stringExpression.getValue());
        var expression2 = Bindings.format("value=%s", value);
        {
            /**/
            Bindings.max(new SimpleIntegerProperty(1),new SimpleIntegerProperty(2));
        }

        {
            /*调用get()方法时，执行call(),返回结果*/
            var stringBinding=Bindings.createStringBinding(()->{
                System.out.println("asd");
                return "213";
            });
            System.out.println(stringBinding.get());
        }
        {
            var student= new Student();
            student.setStudentName(new Name("陈志源"));
            var userProperty = new SimpleObjectProperty<>(student);
            var stringBinding = Bindings.selectString(userProperty, "studentName", "name");
            System.out.println(stringBinding.get());
            System.out.println(userProperty.getValue().studentNameProperty().get().nameProperty().get());
        }


    }
}

