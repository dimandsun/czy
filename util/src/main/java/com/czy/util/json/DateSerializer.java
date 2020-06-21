package com.czy.util.json;

import com.czy.util.time.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * date对象-》日期字符串。
 * @author chenzy
 * @date 2019.12.25
 */
public class DateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers){
        try {
            gen.writeString(DateUtil.data2Str(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
