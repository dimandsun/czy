package com.czy.question.service;

import com.czy.question.model.table.Question;
import com.czy.util.model.ResultVO;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public interface IQuestionService {
    ResultVO insert(Question question);
}
