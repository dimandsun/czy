package com.czy.core.controller;

import com.czy.core.annotation.*;
import com.czy.core.annotation.bean.Controller;
import com.czy.core.annotation.mapping.*;
import com.czy.core.service.ICommonService;
import com.czy.util.model.ResultVO;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
@Controller
@Mapping("/common")
public class CommonController {
    @Auto
    private ICommonService commonService;

    @PostMapping
    public ResultVO insert(Map<String,Object> map){
        return commonService.insert(map);
    }
    @DeleteMapping
    public ResultVO delete(Map<String,Object> map){
        return commonService.delete(map);
    }
    @PutMapping
    public ResultVO update(Map<String,Object> map){
        return commonService.update(map);
    }
    @GetMapping
    public ResultVO get(Map<String,Object> map){
        return commonService.get(map);
    }
}
