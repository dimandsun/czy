package com.czy.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class SimpleStringPropertySerializer extends JsonSerializer<SimpleStringProperty> {
    @Override
    public void serialize(SimpleStringProperty value, JsonGenerator gen, SerializerProvider serializers){
        try {
            gen.writeString(value.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}