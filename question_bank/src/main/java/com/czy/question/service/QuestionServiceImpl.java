package com.czy.question.service;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.Service;
import com.czy.core.util.TableUtil;
import com.czy.question.dao.IQuestionDao;
import com.czy.question.model.table.Question;
import com.czy.util.model.ResultVO;

/**
 * @author chenzy
 * @date 2020-07-21
 */
@Service
public class QuestionServiceImpl implements IQuestionService {
    @Auto
    private IQuestionDao questionDao;

    @Override
    public ResultVO insert(Question question) {
        Integer id=questionDao.insert(TableUtil.getTableName(question.getClass()),question);
        return new ResultVO(id);
    }
}
