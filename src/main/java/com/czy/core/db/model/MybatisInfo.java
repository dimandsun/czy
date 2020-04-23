package com.czy.core.db.model;

/**
 * @author chenzy
 * @description
 * @since 2020-04-14
 */
public class MybatisInfo {
    private TypeAliases typeAliases;

    public TypeAliases getTypeAliases() {
        return typeAliases;
    }

    public void setTypeAliases(TypeAliases typeAliases) {
        this.typeAliases = typeAliases;
    }


    public MybatisInfo(TypeAliases typeAliases) {
        this.typeAliases = typeAliases;
    }
}
