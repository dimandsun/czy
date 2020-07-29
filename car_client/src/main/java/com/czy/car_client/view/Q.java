package com.czy.car_client.view;

import com.czy.core.dispatch.Quest;
import com.czy.core.dispatch.Response;
import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.model.ResultVO;
import com.czy.util.model.StringMap;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * @author chenzy
 * @since 2020-06-16
 */
public class Q {
    public static ReadOnlyObjectProperty<Response<ResultVO>> delete(String url, StringMap data) {
        return exec(QuestMethodEnum.Delete,url,data);
    }
    public static ReadOnlyObjectProperty<Response<ResultVO>> put(String url, StringMap data) {
        return exec(QuestMethodEnum.Put,url,data);
    }

    public static ReadOnlyObjectProperty<Response<ResultVO>> post(String url, StringMap data) {
        return exec(QuestMethodEnum.Post,url,data);
    }

    public static ReadOnlyObjectProperty<Response<ResultVO>> get(String url, StringMap data) {
        return exec(QuestMethodEnum.Get,url,data);
    }

    public static ReadOnlyObjectProperty<Response<ResultVO>> exec(QuestMethodEnum questMethodEnum, String url, StringMap data) {
        var disPatchService = new DisPatchService(new Quest(questMethodEnum, url, data));
        new Thread(disPatchService).start();
        return disPatchService.valueProperty();
    }
}
