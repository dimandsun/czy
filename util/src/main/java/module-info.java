/**
 * @author chenzy
 *
 * @since 2020-05-11
 */open module com.czy.util {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires cglib;
    requires java.xml;
    requires org.yaml.snakeyaml;
    requires poi.ooxml;
    requires poi.scratchpad;
    requires poi;
    requires xmlbeans;
    exports com.czy.util;
    exports com.czy.util.model;
    exports com.czy.util.enums;
    exports com.czy.util.json;
    exports com.czy.util.tcp;
    exports com.czy.util.io;
    exports com.czy.util.text;
    exports com.czy.util.time;
    exports com.czy.util.annotation;
    exports com.czy.util.list;
}