package com.czy.fx.myfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

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

    public Mybutton setTooltip(String tip) {
        setTooltip(new Tooltip(tip));
        return this;
    }
}
