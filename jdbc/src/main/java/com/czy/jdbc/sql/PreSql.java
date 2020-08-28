package com.czy.jdbc.sql;

import com.czy.util.text.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-07-22
 * 预处理sql语句，
 */
public class PreSql {
    private String sql;
    private List<Object> pars;
    /*sql拼接到末尾了*/
    private Boolean isEnd=false;

    public Boolean isEnd() {
        return isEnd;
    }
    public void isEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addSQLText(String sql) {
        this.sql += sql;
    }
    public PreSql append(Optional<PreSql> preSql) {
        preSql.ifPresent(sql -> {
            this.sql += sql.sql;
            this.pars.addAll(sql.getPars());
        });
        return this;
    }
    public PreSql replace(String reg, String value) {
        this.sql = this.sql.replaceAll(reg,value);
        return this;
    }
    public PreSql replace(String reg, Optional<PreSql> preSql) {
        preSql.ifPresent(sql -> {
            this.sql = this.sql.replaceAll(reg,sql.getSql());
            this.pars.addAll(sql.getPars());
        });
        return this;
    }
    public List<Object> getPars() {
        return List.copyOf(pars);
    }

    public void setPars(List<Object> pars) {
        this.pars = pars;
    }


    public PreSql(String sql, List<Object> pars) {
        this.sql = sql;
        this.pars = pars;
    }

    public PreSql addPars(Collection pars) {
        if (pars==null||pars.isEmpty()){
            return this;
        }
        this.pars.addAll(pars);
        return this;
    }
    public PreSql addPar(Object par) {
        if (StringUtil.isBlank(par)){
            return this;
        }
        this.pars.add(par);
        return this;
    }
    public boolean isEmptyPar() {
        return this.pars==null||this.pars.isEmpty();
    }
}
