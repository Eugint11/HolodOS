package com.holod.HolodOS.externalApi.lowCarbRecipes.recipe;

import com.holod.HolodOS.externalApi.parsers.JsonRecipeParcer;
import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import com.holod.HolodOS.buisinesObject.recipe.RecipeResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@Component
public class ExternalRecipeAPI implements ExternalRecipe {

    private List<Recipe> recipelist = new ArrayList<>();
    private int lastId = 0;

    @Override
    public RecipeResponse getRecipe(Map<String, String> params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RecipeApi recipeApi = new RecipeApi();
            recipeApi.setParams(params);
            ResponseEntity<String> response = restTemplate.exchange(
                    recipeApi.getURLWithParams(),
                    HttpMethod.GET,
                    RecipeApi.getHeaders(),
                    String.class);
            log.info(String.join("; ",params.keySet().stream().map(x -> x+": "+params.get(x)).collect(Collectors.toList())));
            log.info(response.getBody());
            JsonRecipeParcer parcer = new JsonRecipeParcer();
            return new RecipeResponse(HttpStatus.valueOf(response.getStatusCode().value()), parcer.getRecipe(response.getBody()));
        } catch (HttpClientErrorException e) {
            return new RecipeResponse(HttpStatus.valueOf(e.getStatusCode().value()), null);
        }
    }

    @Override
    public RecipeResponse getRecipe(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            RecipeApi recipeApi = new RecipeApi();
            ResponseEntity<String> response = restTemplate.exchange(
                    recipeApi.getURL(),
                    HttpMethod.GET,
                    RecipeApi.getHeaders(),
                    String.class);
            log.info(response.getBody());
            JsonRecipeParcer parcer = new JsonRecipeParcer();
            return new RecipeResponse(HttpStatus.valueOf(response.getStatusCode().value()), parcer.getRecipe(response.getBody()));
        } catch (HttpClientErrorException e) {
            return new RecipeResponse(HttpStatus.valueOf(e.getStatusCode().value()), null);
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
