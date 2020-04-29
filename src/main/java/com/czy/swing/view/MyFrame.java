package com.czy.swing.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author chenzy
 * @description
 * @since 2020-04-29
 */
public class MyFrame extends JFrame {
    public MyFrame(String title, Integer width, Integer height,Integer x,Integer y, LayoutManager layoutManager){
        super(title);
        setLayout(layoutManager);
        setSize(width,height);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
    @Override
    public MyFrame add(Component comp) {
        super.add(comp);
        return this;
    }
}