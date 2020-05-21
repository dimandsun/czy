package com.czy.fx.test.test52_binding;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import static com.czy.util.StringUtil.println;

/**
 * @author chenzy
 * @since 2020-05-21
 * @description 单向绑定，双向绑定
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
        anchorPane.getChildren().addAll(btn,text1,text2,sliderBtn,slider);

        text1.textProperty().bindBidirectional(text2.textProperty(), new StringConverter<>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                println("getHeight:{},getLayoutY:{},getPrefHeight:{},getScaleY:{},getTranslateY:{}"
                        ,text1.getHeight(),text1.getLayoutY(),text1.getPrefHeight(),text1.getScaleY(),text1.getTranslateY());
                return string+"-";
            }
        });
        var scale = new SimpleIntegerProperty(2);
        btn.prefWidthProperty().bind(anchorPane.widthProperty().divide(scale));
        sliderBtn.translateXProperty().bind(slider.valueProperty());
        FXUtil.setDefaultValue(primaryStage,anchorPane);

        AnchorPane.setTopAnchor(text1,btn.getHeight());
        AnchorPane.setTopAnchor(text2,btn.getHeight());
        AnchorPane.setLeftAnchor(text2,text1.getWidth());
        AnchorPane.setTopAnchor(sliderBtn,btn.getHeight()+btn.getHeight());
        AnchorPane.setTopAnchor(slider,btn.getHeight()+btn.getHeight()+sliderBtn.getHeight());
    }
    public static void main(String[] args) {
        bind();

//        danBind();
//        shuangBind();
    }
    /**
     * 绑定计算，实时绑定。使用值的时候才知道值是多少。
     */
    public static void  bind(){
        var a= new SimpleIntegerProperty(1);
        var b= new SimpleIntegerProperty(2);
        /*b的值不会改变，返回的值为4*/
        var result =b.add(2);
        var result1=b.add(a);

        println("a:{},b:{},\nb.add(2):{}\nb.add(a):{}",a.get(),b.get(),result.get(),result1.getValue());
        b.set(3);
        println("a:{},b:{},\nb.add(2):{}\nb.add(a):{}",a.get(),b.get(),result.get(),result1.getValue());

        /*判断大小*/
        a.greaterThan(b);
        a.lessThan(b);
    }


    /**
     * 单向绑定x绑定到y上。x值随y值改变而改变
     *    绑定后x值被束缚。x.set()会报错，即无法直接给x设值，只能给y设值，y值改变了x值自然改变
     */
    public static void danBind(){
        var x=new SimpleIntegerProperty(1);
        var y=new SimpleIntegerProperty(2);
        x.bind(y);
        println("单向绑定:({},{})",x.get(),y.get());
        println("x是否被束缚{}",x.isBound());
        /*解绑*/
        x.unbind();
    }

    /**
     * 双向绑定:双方都不会被束缚，双方值同步
     */
    public static void shuangBind(){
        var x=new SimpleIntegerProperty(1);
        var y=new SimpleIntegerProperty(2);
        /*初始化时，以y为主*/
        x.bindBidirectional(y);
        println("双向绑定:({},{})",x.get(),y.get());
        x.set(3);y.set(4);
        println("双向绑定:({},{})",x.get(),y.get());
        /*解绑*/
        x.unbindBidirectional(y);
    }

}
