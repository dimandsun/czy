package com.czy.car.view;

import com.czy.core.dispatch.Dispatch;
import com.czy.core.dispatch.Quest;
import com.czy.core.dispatch.Response;
import com.czy.util.model.ResultVO;
import javafx.concurrent.Task;

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
        ResultVO resultVO = Dispatch.getInstance().exec(quest);
        response = new Response(quest, resultVO);
        return response;
    }


}
