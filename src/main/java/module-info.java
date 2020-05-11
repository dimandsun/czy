/**
 * @author chenzy
 * @description
 * @since 2020/5/3
 */open module com.czy.base {
    requires cglib;
    requires slf4j.api;
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
    requires log4j.core;
    requires log4j.api;
    exports com.czy.util.model;
    exports com.czy.util;
    exports com.czy.util.enums;
}