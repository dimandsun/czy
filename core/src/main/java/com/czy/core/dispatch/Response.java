package com.czy.core.dispatch;


/**
 * @author chenzy
 * @since 2020-06-16
 */
public class Response<T> {
    private Quest quest;
    private T data;

    public Response(Quest quest, T data) {
        this.quest = quest;
        this.data = data;
    }

    public Response(Quest quest) {
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
