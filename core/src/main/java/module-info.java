/**
 * @author chenzy
 * 
 * @since 2020/5/3
 */open module com.czy.core {
    requires cglib;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.lang3;
    requires java.sql;
    requires java.desktop;
    requires druid;
    requires jedis;
    requires org.mybatis;
    requires org.yaml.snakeyaml;
    requires java.naming;
    requires org.objectweb.asm;
    requires jyaml;
    requires com.czy.util;
    requires com.czy.log;
    exports com.czy.core.util;
    exports com.czy.user.model;
    exports com.czy.swing.view;
    exports com.czy.core.annotation.db;
    exports com.czy.core.annotation.bean;
    exports com.czy.core.annotation;
    exports com.czy.core.redis.service;
    exports com.czy.core.service;
    exports com.czy.core.annotation.mapping;
    exports com.czy.core.model;
    exports com.czy.core;
    exports com.czy.core.enums;
    exports com.czy.core.dispatch;
    exports com.czy.core.db.config;
}