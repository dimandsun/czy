package com.czy.question.dao;

import com.czy.core.annotation.bean.Dao;
import com.czy.jdbc.sql.annotation.InsertSQL;
import com.czy.question.model.table.Question;
import com.czy.util.annotation.Par;

/**
 * @author chenzy
 * @date 2020-07-21
 */
@Dao
public interface IQuestionDao {
    @InsertSQL
    Integer insert(@Par("tableName")String tableName,@Par("setPar") Question question);
}
