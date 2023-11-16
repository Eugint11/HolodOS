package com.holod.HolodOS.storage.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.holod.HolodOS.recipe.Recipe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JsonRecipeParcer {
    public Optional<List<Recipe>> getRecipe(String responseBody) throws JsonParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Recipe> recipe = List.of(objectMapper.readValue(responseBody, new TypeReference<>() {
            }));
            log.info(recipe.get(0).getName() + ": " + Arrays.stream(recipe.get(0).getSteps()).toList());
            log.info("Всё!");
            return Optional.of(recipe);
        } catch (JsonProcessingException e) {
            log.info("Всё плохо!");
            return Optional.empty();
        }
    }
}
