package com.czy.util.json;

import com.czy.util.StringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BooleanSerializer extends JsonSerializer<Boolean> {
    @Override
    public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            gen.writeNumber(StringUtil.getInt(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
