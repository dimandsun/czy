package com.czy.fx.myfx;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


/**
 * @author chenzy
 * @description
 * @since 2020-05-09
 */
public class MyTextField extends TextField {
    public MyTextField() {
    }

    public MyTextField(String text) {
        super(text);
    }

    /**
     * 有焦点的组件才实现了此监听
     * @param handler
     * @return
     */
    public MyTextField onKeyTyped(EventHandler<? super KeyEvent> handler) {
        this.setOnKeyTyped(handler);
        return this;
    }
}
