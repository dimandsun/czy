package com.czy.question.controller;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.Controller;
import com.czy.core.annotation.mapping.Mapping;
import com.czy.core.annotation.mapping.PutMapping;
import com.czy.question.model.table.Question;
import com.czy.question.service.IQuestionService;
import com.czy.util.model.ResultVO;

/**
 * @author chenzy
 * @date 2020-07-21
 */
@Controller
@Mapping("question")
public class QuestionController {
    @Auto
    private IQuestionService questionService;

    @PutMapping
    public ResultVO insert(Question question){
        return questionService.insert(question);
    }

}
