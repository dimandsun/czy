package com.czy.http;
import java.util.EventObject;
public final class ContainerEvent<T> extends EventObject {
    private T data = null;

    private String type = null;

    public ContainerEvent(Container container, String type, T data) {
        super(container);
        this.type = type;
        this.data = data;
    }
    public T getData() {
        return data;
    }
    public Container getContainer() {
        return (Container) getSource();
    }
    public String getType() {
        return this.type;
    }
    @Override
    public String toString() {
        return ("ContainerEvent['" + getContainer() + "','" +
                getType() + "','" + getData() + "']");

    }


}
