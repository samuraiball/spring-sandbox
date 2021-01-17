package dev.hirooka.rsocket.conifg;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.util.Map;

public class EncodingUtils {

    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;

    private final TypeReference<Map<String, Object>> typeReference = new TypeReference() {
    };

    public EncodingUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectReader = this.objectMapper.readerFor(typeReference);
    }

    public <T> T decode(String json, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T> String encode(T object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T> String encodeMetadata(Map<String, Object> metadata) {
        try {
            return this.objectMapper.writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T> Map<String, Object> decodeMetadata(String json) {
        try {
            return this.objectReader.readValue(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
