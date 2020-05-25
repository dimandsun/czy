package com.czy.fx.myfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author chenzy
 * @description
 * @since 2020-05-23
 */
public class Mybutton extends Button {

    public Mybutton() {
    }

    public Mybutton(String text) {
        super(text);
    }
    public Mybutton onClick(EventHandler<ActionEvent> eventHandler){
        this.setOnAction(eventHandler);
        return this;
    }
    public Mybutton onKeyPressed(EventHandler<? super KeyEvent> eventHandler){
        this.setOnKeyPressed(eventHandler);

        return this;
    }

    /**
     * 鼠标拖动组件。拖动过程中不断执行监听的方法
     * @return
     */
    public Mybutton onDragged(EventHandler<? super MouseEvent> handler){
        this.setOnMouseDragged(handler);
        return this;
    }

    /**
     * 组件开始拖动,监听方法只执行一次
     * @param handler
     * @return
     */
    public Mybutton onStartDragged(EventHandler<? super MouseEvent> handler){
        this.setOnDragDetected(handler);
        return this;
    }
    public Mybutton onMouseIn(EventHandler<? super MouseEvent> handler){
        this.setOnMouseEntered(handler);
        return this;
    }
    public Mybutton onMouseOut(EventHandler<? super MouseEvent> handler){
        this.setOnMouseEntered(handler);
        return this;
    }
    /**
     * 鼠标在组件上拖动时执行，执行前提是 在setOnDragDetected中startFullDrag();
     * @param handler
     * @return
     */
    public Mybutton onDragIn(EventHandler<? super MouseDragEvent> handler){
        this.setOnMouseDragOver(handler);
        return this;
    }
    public Mybutton onDragEnd(EventHandler<? super MouseDragEvent> handler){
        this.setOnMouseDragReleased(handler);
        return this;
    }
    public Mybutton onKeyReleased(EventHandler<? super KeyEvent> eventHandler){
        this.setOnKeyReleased(eventHandler);
        return this;
    }
    public Mybutton setTooltip(String tip) {
        setTooltip(new Tooltip(tip));
        return this;
    }
}
