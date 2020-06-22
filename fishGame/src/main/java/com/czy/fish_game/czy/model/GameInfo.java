package com.czy.fish_game.czy.model;

import com.czy.fish_game.czy.emuns.GameStatusEnum;
import com.czy.fish_game.mainsurface.MainSurface;
import com.czy.fish_game.manager.ShoalManager;
import com.czy.fish_game.manager.SoundManager;
import com.czy.fish_game.model.fish.Fish;

import java.util.ArrayList;

/**
 * @author chenzy
 *  游戏对象
 * @since 2020-05-08
 */
public class GameInfo {
    /*游戏者*/
    private String player;
    /*游戏窗口宽度*/
    private Integer width;
    /*游戏窗口高度*/
    private Integer height;
    /*游戏状态*/
    private GameStatusEnum status;
    /*当前游戏得分*/
    private int score = 100;
    /*当前游戏等级*/
    private Integer level = 1;

    private MainSurface surface; // 主屏幕
    private ArrayList<Fish> fish = new ArrayList<Fish>(); // 所有的鱼
    private ShoalManager shoalManager; // 鱼群管理器
    private SoundManager soundManager;//音效管理器
    private float cannonLayoutX;            //大炮旋转X坐标
    private float cannonLayoutY;            //大炮旋转Y坐标

    private static GameInfo instance = new GameInfo();
    private GameInfo() {
    }

    public static GameInfo getInstance() {
        return instance;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public MainSurface getSurface() {
        return surface;
    }

    public void setSurface(MainSurface surface) {
        this.surface = surface;
    }

    public ArrayList<Fish> getFish() {
        return fish;
    }

    public void setFish(ArrayList<Fish> fish) {
        this.fish = fish;
    }

    public ShoalManager getShoalManager() {
        return shoalManager;
    }

    public void setShoalManager(ShoalManager shoalManager) {
        this.shoalManager = shoalManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public float getCannonLayoutX() {
        return cannonLayoutX;
    }

    public void setCannonLayoutX(float cannonLayoutX) {
        this.cannonLayoutX = cannonLayoutX;
    }

    public float getCannonLayoutY() {
        return cannonLayoutY;
    }

    public void setCannonLayoutY(float cannonLayoutY) {
        this.cannonLayoutY = cannonLayoutY;
    }
}
