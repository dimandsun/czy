package com.czy.core.db.model;

/**
 * @author chenzy
 * @description
 * @since 2020-04-14
 */
public class TypeAliases {
    private String packageName;

    public TypeAliases() {
    }

    public TypeAliases(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
