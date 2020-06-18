package com.czy.car_client.view;

import com.czy.car_client.CarClientInfo;
import com.czy.core.dispatch.Quest;
import com.czy.core.dispatch.Response;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.ResultVO;
import javafx.concurrent.Task;

import static com.czy.util.tcp.SocketUtil.readData;
import static com.czy.util.tcp.SocketUtil.sendData;

/**
 * @author chenzy
 * @since 2020-06-16
 */
public class DisPatchService extends Task<Response<ResultVO>> {
    private Quest quest;
    private Response<ResultVO> response;

    public DisPatchService(Quest quest) {
        this.quest = quest;
    }

    @Override
    protected Response<ResultVO> call() {
        var socket = CarClientInfo.getInstance().getSocket();
        sendData(socket, JsonUtil.model2Str(quest));
        var resultData=readData(socket);
        var resultVO = JsonUtil.str2Model(resultData, ResultVO.class);
        response = new Response(quest, resultVO);
        return response;
    }


}
