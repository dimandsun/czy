package com.czy.swing.view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author chenzy
 * @description
 * @since 2020-04-29
 */
public class MyButton extends JButton {

    public MyButton(String text) {
        super(text);
    }

    public MyButton addListener(ActionListener l) {
        super.addActionListener(l);
        return this;
    }
}
