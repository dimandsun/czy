package com.czy.frame.util.json;

import com.czy.frame.util.DateUtil;
import com.czy.frame.util.StringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * jason转对象时，日期字符串转date对象。
 * @author chenzy
 * @date 2019.12.25
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt){

        try {
           String dateStr = p.getText();
           if (StringUtil.isBlank(dateStr)){
               return null;
           }else

           return DateUtil.str2Date(dateStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
