package com.czy.http;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public interface Container {
    String ADD_CHILD_EVENT = "addChild";
    String REMOVE_CHILD_EVENT = "removeChild";
    String ADD_VALVE_EVENT = "addValve";
    String REMOVE_VALVE_EVENT = "removeValve";
    String getName();

    void fireContainerEvent(String type, Object data);
    void setParent(Container container);

    List<Container> findChildren();

    void start();
    Loader getLoader();
}
