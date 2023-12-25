package com.holod.HolodOS.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
            log.info(recipe.get(0).getName() + ": " + Arrays.stream(recipe.get(0).getRecipeGoodSet().toArray()));
            log.info("Всё!");
            return Optional.of(recipe);
        } catch (JsonProcessingException e) {
            log.info("Всё плохо!");
            return Optional.empty();
        }
    }

    public String setRecipes(List<Recipe> recipes) throws JsonParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
            String s = objectMapper.writeValueAsString(recipes);
            return s;
        } catch (JsonProcessingException e) {
            log.info("Всё плохо!");
            return null;
        }
    }

}
