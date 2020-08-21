package com.czy.core;

import com.czy.core.annotation.bean.Controller;
import com.czy.core.annotation.mapping.Mapping;
import com.czy.util.enums.ResCodeEnum;
import com.czy.util.model.ResultVO;


/**
 * @author chenzy
 * @since 2020-02-27
 *  拦截404、500错误
 */
@Controller
public class ExceptionController {

    @Mapping("/error_404")
    public ResultVO error_404(){
        return new ResultVO(ResCodeEnum.NotFound);
    }

    @Mapping("/error_500")
    public ResultVO error_500(){
        return new ResultVO(ResCodeEnum.DBExce);
    }
}
