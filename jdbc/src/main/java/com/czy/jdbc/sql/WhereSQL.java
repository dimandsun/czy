package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.RelationEnum;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class WhereSQL {
    private PreSql preSql;

    private WhereSQL(PreSql preSql) {
        this.preSql = preSql;
    }
    public static WhereSQL newInstance() {
        return new WhereSQL(new PreSql(" where ", new ArrayList<>()));
    }
    public Optional<PreSql> getEndSql() {
        if (preSql == null) {
            return Optional.empty();
        }
        if (preSql.getSql().endsWith("where ")) {
            return Optional.empty();
        }
        return Optional.of(preSql);
    }

    public WhereSQL and() {
        preSql.addSQLText("and ");
        return this;
    }

    public WhereSQL or() {
        preSql.addSQLText("or ");
        return this;
    }

    /**
     * 小于 &lt;
     */
    public <T> WhereSQL lt(String columnName, T value) {
        return setWhereSql(RelationEnum.LT, columnName, value);
    }

    /**
     * 小于等于 	&le;
     */
    public <T> WhereSQL le(String columnName, T value) {
        return setWhereSql(RelationEnum.LE, columnName, value);
    }

    public <T> WhereSQL equal(Map<String, Object> columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        columnMap.forEach((key, value) -> equal(key, value));
        return this;
    }

    public <T> WhereSQL equal(String columnName, T value) {
        return setWhereSql(RelationEnum.Equal, columnName, value);
    }

    public <T> WhereSQL notEqual(String columnName, T value) {
        return setWhereSql(RelationEnum.NotEqual, columnName, value);
    }

    /**
     * 大于 &gt;
     */
    public <T> WhereSQL gt(String columnName, T value) {
        return setWhereSql(RelationEnum.GT, columnName, value);
    }

    /**
     * 大于等于 	&ge;
     */
    public <T> WhereSQL ge(String columnName, T value) {
        return setWhereSql(RelationEnum.GE, columnName, value);
    }

    public <T> WhereSQL like(String columnName, T value) {
        return setWhereSql(RelationEnum.Like, columnName, value);
    }

    public <T> WhereSQL likeLeft(String columnName, T value) {
        return setWhereSql(RelationEnum.LikeLeft, columnName, value);
    }

    public <T> WhereSQL likeRight(String columnName, T value) {
        return setWhereSql(RelationEnum.LikeRight, columnName, value);
    }

    private Boolean isEmpty() {
        return preSql.getSql().equals(" where ");
    }

    private <T> WhereSQL setWhereSql(RelationEnum relationEnum, String columnName, T value) {
        if (StringUtil.isBlankOr(columnName, value)) {
            return this;
        }
        preSql.addSQLText((isEmpty() ? "" : " and ") + columnName + relationEnum.getValue());
        preSql.addPar(value);
        return this;
    }

    public WhereSQL condition(String sqlText) {
        preSql.addSQLText((isEmpty() ? "" : " and ") + sqlText);
        return this;
    }
}
