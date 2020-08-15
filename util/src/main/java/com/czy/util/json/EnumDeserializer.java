package com.czy.util.json;

import com.czy.util.EnumUtil;
import com.czy.util.enums.IEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

/**
 * json=>对象
 */
public class EnumDeserializer<T extends IEnum> extends JsonDeserializer<T> implements ContextualDeserializer{
    private Class<T> targetClass = null;

    public EnumDeserializer() {
        System.out.println(213);
    }

    public EnumDeserializer(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) {
//        if(targetClass!=null&&IEnum.class.isAssignableFrom(targetClass)){
            try {
                String value = p.getText();
                return EnumUtil.getIEnum(targetClass,value);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
        return null;
    }

    @Override @SuppressWarnings("unchecked")
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        Class<T> targetClass = (Class<T>) ctxt.getContextualType().getRawClass();
        return new EnumDeserializer(targetClass);
    }
}
