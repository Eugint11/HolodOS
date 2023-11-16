package com.holod.HolodOS.storage.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holod.HolodOS.recipe.RecipeResponse;
import com.holod.HolodOS.recipe.Recipe;
import com.holod.HolodOS.recipe.RecipeApi;
import com.holod.HolodOS.typeAdapter.LocalDateTypeAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Data
@Component
public class InMemoryRecipeStorage implements RecipeStorage {

    private List<Recipe> recipelist = new ArrayList<>();
    private int lastId = 0;

    @Override
    public RecipeResponse getRecipe(Map<String, String> params) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();

        try {
            RestTemplate restTemplate = new RestTemplate();
            RecipeApi recipeApi = new RecipeApi();
            recipeApi.setParams(params);
            ResponseEntity<String> response = restTemplate.exchange(
                    recipeApi.setURL(),
                    HttpMethod.GET,
                    RecipeApi.setHeaders(),
                    String.class);
            log.info(response.getBody());
            JsonRecipeParcer parcer = new JsonRecipeParcer();
            return new RecipeResponse(HttpStatus.valueOf(response.getStatusCode().value()), parcer.getRecipe(response.getBody()));
        } catch (HttpClientErrorException e) {
            return new RecipeResponse(HttpStatus.valueOf(e.getStatusCode().value()), Optional.empty());
        }
    }

    @Override
    public RecipeResponse addRecipe(Recipe recipe) {
        Recipe newRecipe = recipe.toBuilder().id(getLastId()).build();
        recipelist.add(newRecipe);
        log.info("Рецепт добавлен." + newRecipe.toString());
        return new RecipeResponse(HttpStatus.OK, Optional.of(List.of(newRecipe)));
    }

    @Override
    public RecipeResponse updateRecipe(@Valid Recipe recipe) {
        Optional<Recipe> oldRecipe = recipelist.stream().filter(x -> x.getId().equals(recipe.getId())).findFirst();
        if (oldRecipe.isPresent()) {
            recipelist.remove(oldRecipe.get());
            recipelist.add(recipe);
            log.info("Рецепт добавлен." + recipe.toString());
            return new RecipeResponse(HttpStatus.OK, Optional.of(List.of(recipe)));
        }
        log.info("Рецепт не найден." + recipe.toString());
        return new RecipeResponse(HttpStatus.OK, Optional.empty());
    }

    @Override
    public boolean deleteRecipe(Integer id) {
        Optional<Recipe> oldRecipe = recipelist.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (oldRecipe.isPresent()) {
            recipelist.remove(oldRecipe.get());
            log.info("Рецепт удален." + id);
            return true;
        }
        log.info("Рецепт не найден.");
        return false;
    }

    private int getLastId() {
        return lastId++;
    }
}
