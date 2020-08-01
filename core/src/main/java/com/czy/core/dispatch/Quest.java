package com.czy.core.dispatch;

import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.model.StringMap;

/**
 * @author chenzy
 * @since 2020-06-16
 */
public class Quest {
    private String url;
    private QuestMethodEnum questMethodEnum;
    private StringMap data;
    public Quest(String url) {
        this.questMethodEnum = QuestMethodEnum.ALL;
        this.url = url;
        this.data = null;
    }
    public Quest(String url, StringMap data) {
        this.questMethodEnum = QuestMethodEnum.ALL;
        this.url = url;
        this.data = data;
    }
    public Quest(QuestMethodEnum questMethodEnum, String url, StringMap data) {
        this.questMethodEnum = questMethodEnum;
        this.url = url;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public QuestMethodEnum getQuestMethodEnum() {
        return questMethodEnum;
    }

    public void setQuestMethodEnum(QuestMethodEnum questMethodEnum) {
        this.questMethodEnum = questMethodEnum;
    }

    public StringMap getData() {
        return data;
    }

    public void setData(StringMap data) {
        this.data = data;
    }
}
