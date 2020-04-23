package com.czy.util.json;

import com.czy.util.StringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * 反序列化，处理字符串。对空字符串的处理
 * @author chenzy
 * @date 2019.12.19
 */
public class StringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
        String result = p.getText();
        return StringUtil.isBlank(result)?null:result;
    }
}
