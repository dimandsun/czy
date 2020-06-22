package com.czy.fish_game.czy.emuns;

import com.czy.util.enums.IEnum;
/**
 * @author chenzy
 * @since 2020-05-08
 *  游戏对象状态，
 * 初始化游戏时：Start
 * 初始化游戏后，玩家未开始游戏：Wait
 * 玩家开始游戏且正常游戏时：Play
 * 玩家在游戏中暂停：Pause
 * 一局游戏结束后，状态变回Wait
 */
public enum GameStatusEnum implements IEnum<Integer>{
    Start(1,"启动游戏中"),Wait(2,"等待游戏开始"),Play(1,"正常游戏中"),Pause(2,"暂停");

    private Integer id;
    private String msg;

    GameStatusEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return id;
    }
}
