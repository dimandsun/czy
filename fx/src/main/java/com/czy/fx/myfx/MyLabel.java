package com.czy.fx.myfx;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author chenzy
 * @description
 * @since 2020-05-09
 */
public class MyLabel extends Label {
    public MyLabel(String text) {
        super(text);
    }

    public MyLabel(String text, Node graphic) {
        super(text, graphic);
    }
}
