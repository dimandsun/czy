package com.czy.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class SimpleStringPropertyDeserializer extends JsonDeserializer<SimpleStringProperty> {
    @Override
    public SimpleStringProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            String value = p.getText();
            return new SimpleStringProperty(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}