package com.czy.fx.test.test58_event;

import com.czy.fx.myfx.MyTextField;
import com.czy.fx.myfx.Mybutton;
import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @author chenzy
 * 
 * @since 2020-05-25
 */
public class EventTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var box = new HBox();
        box.getChildren().addAll(new Mybutton("按下事件").onKeyPressed(event -> {
                    /*大多时候事件源和事件目标一样*/
                    event.getSource();
                    event.getTarget();
                })
                ,new Mybutton("拖动").onDragged(event -> System.out.print("")).onStartDragged(event -> {
                    System.out.println("onDraggedDetected");
                    event.isStillSincePress();//拖动的时候是否真正有位移
                    Mybutton mybutton = (Mybutton) event.getSource();
                    mybutton.startFullDrag();
                }).onDragIn(event -> {
                    System.out.println("onDragOver");
                    //此时事件源和事件目标可能不一样
                    event.getSource();
                    event.getTarget();
                    //从哪开始拖动
                    event.getGestureSource();
                })
                ,new Mybutton("鼠标事件").onDragged(event -> {

                })
                , new MyTextField().onKeyTyped(event -> {
                    event.getCode().getCode();
                    System.out.println("123");
                })

        );
        var circle=new Circle(100, Color.RED);
        //不接受父组件的事件，默认false
//        circle.setMouseTransparent(true);

        /**
         * 圆圈占用的区域都可以监听事件，默认false
         */
        circle.setPickOnBounds(true);
        Button btn =new Mybutton("嘿嘿").onClick(event -> {
            System.out.println("s黑河");
        });
        btn.setOnAction(event -> {
            System.out.println("setOnAction");
        });
        btn.setOnMouseClicked(event -> {
            System.out.println("setOnMouseClicked");
        });
        circle.setOnMouseClicked(event -> {
            System.out.println("圆圈被点击");
            //点击信息
            var pickResult=event.getPickResult();
            //是否是触摸屏
            event.isSynthesized();
            //事件传递
            Event.fireEvent(btn,event);
            //或者这样
            event.copyFor(btn,btn);
        });
        //事件：
        circle.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
            System.out.println("addEventFilter");
        });
        box.getChildren().add(circle);
        anchorPane.getChildren().add(box);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }
}
