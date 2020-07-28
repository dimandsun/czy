package com.czy.util.common;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public interface LifeCycle {
    enum Status implements IEnum<String>{
        Init,Start,Exec,Pause,Stop,Destroy, Loop;
        @Override
        public String getValue() {
            return name();
        }
    }
    Object init();
    Object start();
    Object exec();
    default Object pause(){return null;}
    default Object stop(){return null;}
    default Object Loop(){return null;}
    Object destroy();
}
