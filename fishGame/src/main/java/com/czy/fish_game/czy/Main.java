package com.czy.fish_game.czy;

import com.czy.fish_game.czy.emuns.GameStatusEnum;
import com.czy.fish_game.czy.model.GameInfo;

import javax.swing.*;

/**
 * @author chenzy
 * @description
 * @since 2020-05-08
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        var gameInfo = GameInfo.getInstance();
        gameInfo.setWidth(2000);
        gameInfo.setHeight(1000);
        gameInfo.setStatus(GameStatusEnum.Start);
        frame.setSize(gameInfo.getWidth(), gameInfo.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
