package com.holod.HolodOS.internalApi.controller;

import com.holod.HolodOS.externalApi.parsers.JsonParser;
import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import com.holod.HolodOS.internalApi.service.recipe.RecipeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.*;

@Slf4j
@Controller
@RequestMapping(path = "/recipe")
public class RecipeController {
    private JsonParser<Recipe> jsonParser = new JsonParser();
    RecipeServiceImp recipeServiceImp;

    @Autowired
    public RecipeController(RecipeServiceImp recipeServiceImp) {
        this.recipeServiceImp = recipeServiceImp;
    }

    @GetMapping
    public ResponseEntity getRecipe(@Valid
                                    @RequestParam(value = "name", defaultValue = "null") String name,
                                    @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit,
                                    @RequestParam(value = "ingredients", defaultValue = "null") List<String> ingredients
    ) {
        try {
            List<Recipe> recipeList;
            recipeList = recipeServiceImp.read(name, ingredients, limit);
            if (!recipeList.isEmpty()) {
                recipeList.forEach(recipe -> recipe.getRecipeGoodSet().forEach(recipeGood -> recipeGood.setRecipe(null)));
                return new ResponseEntity(jsonParser.toJson(recipeList), HttpStatus.OK);
            }
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } catch (PSQLException e) {
            log.info("Не найдено рецептов в базе по запросу:" +
                    " name: " + name +
                    " limit: " + limit +
                    " ingredients: " + ingredients.toString());
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    ResponseEntity addRecipe(@Valid @RequestBody Recipe recipe) {
        try {
            recipeServiceImp.create(recipe);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    ResponseEntity updateRecipe(@Valid @RequestBody Recipe recipe) {
        try {
            recipeServiceImp.update(recipe, recipe.getId());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    ResponseEntity deleteRecipe(@Positive @RequestParam Integer id) {
        try {
            boolean optionalRecipe = recipeServiceImp.delete(id);
            return new ResponseEntity(optionalRecipe, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }
}
