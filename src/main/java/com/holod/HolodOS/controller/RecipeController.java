package com.holod.HolodOS.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holod.HolodOS.recipe.Recipe;
import com.holod.HolodOS.recipe.RecipeResponse;
import com.holod.HolodOS.storage.recipe.InMemoryRecipeStorage;
import com.holod.HolodOS.typeAdapter.LocalDateTypeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(path = "/recipe")
public class RecipeController {

    InMemoryRecipeStorage recipeStorage = new InMemoryRecipeStorage();

    @GetMapping
    public ResponseEntity getRecipe(@Valid @RequestParam Map<String, String> params) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try {
            RecipeResponse recipes = recipeStorage.getRecipe(params);
            if (recipes.getRecipes().isPresent())
                return new ResponseEntity(gson.toJson(recipes.getRecipes()), recipes.getHttpStatus());
            else
                return new ResponseEntity("", recipes.getHttpStatus());
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    ResponseEntity addRecipe(@Valid @RequestBody Recipe recipe) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try {
            RecipeResponse optionalRecipe = recipeStorage.addRecipe(recipe);
            return new ResponseEntity(gson.toJson(optionalRecipe.getRecipes()), optionalRecipe.getHttpStatus());
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    ResponseEntity updateRecipe(@Valid @RequestBody Recipe recipe) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try {
            RecipeResponse optionalRecipe = recipeStorage.addRecipe(recipe);
            return new ResponseEntity(gson.toJson(optionalRecipe.getRecipes()), optionalRecipe.getHttpStatus());
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    ResponseEntity deleteRecipe(@Positive @RequestParam Integer id) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try {
            boolean optionalRecipe = recipeStorage.deleteRecipe(id);
            return new ResponseEntity(gson.toJson(optionalRecipe), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
