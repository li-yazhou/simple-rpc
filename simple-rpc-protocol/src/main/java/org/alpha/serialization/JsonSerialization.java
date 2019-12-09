package org.alpha.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alpha.api.Serialization;

import java.io.IOException;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
public class JsonSerialization implements Serialization {


    private ObjectMapper objectMapper;

    public JsonSerialization() {
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public <T> byte[] serialize(T obj) {

        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
