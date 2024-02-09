package com.holod.HolodOS.externalApi.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JsonParser<T> {
    public String toJson(List<T> list) throws JsonParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.
                    findAndRegisterModules()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
