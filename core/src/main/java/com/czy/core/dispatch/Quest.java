package com.czy.core.dispatch;

import com.czy.core.enums.QuestEnum;
import com.czy.util.model.StringMap;

/**
 * @author chenzy
 * @since 2020-06-16
 */
public class Quest {
    private String url;
    private QuestEnum questEnum;
    private StringMap data;
    public Quest(String url) {
        this.questEnum = QuestEnum.All;
        this.url = url;
        this.data = null;
    }
    public Quest(String url, StringMap data) {
        this.questEnum = QuestEnum.All;
        this.url = url;
        this.data = data;
    }
    public Quest(QuestEnum questEnum,String url, StringMap data) {
        this.questEnum = questEnum;
        this.url = url;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public QuestEnum getQuestEnum() {
        return questEnum;
    }

    public void setQuestEnum(QuestEnum questEnum) {
        this.questEnum = questEnum;
    }

    public StringMap getData() {
        return data;
    }

    public void setData(StringMap data) {
        this.data = data;
    }
}
