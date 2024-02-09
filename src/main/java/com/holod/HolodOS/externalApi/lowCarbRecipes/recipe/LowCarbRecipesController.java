package com.holod.HolodOS.externalApi.lowCarbRecipes.recipe;

import com.holod.HolodOS.externalApi.parsers.JsonParser;
import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import com.holod.HolodOS.buisinesObject.recipe.RecipeResponse;
import com.holod.HolodOS.internalApi.service.recipe.RecipeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(path = "/lowcarb")
public class LowCarbRecipesController {
    private JsonParser<Recipe> jsonParser = new JsonParser();
    RecipeServiceImp recipeServiceImp;
    ExternalRecipeAPI externalRecipeAPI = new ExternalRecipeAPI();


    @Autowired
    public LowCarbRecipesController(RecipeServiceImp recipeServiceImp) {
        this.recipeServiceImp = recipeServiceImp;
    }

    @GetMapping
    public ResponseEntity getRecipe(@RequestParam Map<String, String> params) {
        try {
            List<Recipe> recipeList;

            RecipeResponse recipes = externalRecipeAPI.getRecipe(params);
            log.info(params.keySet().isEmpty()+
                    " recipes:"+
                    String.join("; ", recipes.getRecipes().get().stream().map(x -> x.getName()).toList()));
            //RecipeResponse recipes = externalRecipeAPI.getRecipe(params);
            if (recipes.getRecipes().isPresent()) {
                recipeList = recipes.getRecipes().get();
                recipeList.forEach(recipe -> recipe.getRecipeGoodSet().forEach(recipeGood -> recipeGood.setRecipe(recipe)));
                recipeList.forEach(recipe -> recipe.getRecipeGoodSet().forEach(recipeGood -> recipeGood.getSize().setRecipeGood(recipeGood)));
                log.info("Соединение с рецептом завершено.");

                recipeList.forEach(x -> {
                    try {
                        List<Recipe> recipeList1 = recipeServiceImp.read(x.getName(), null, 1);
                        log.info(recipeList1.isEmpty()+ ", "+x.getName());
                        if(recipeList1.isEmpty() || !x.getName().contains(recipeList1.get(0).getName())) recipeServiceImp.create(x);
                    } catch (PSQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                log.info("Запись в БД завершена.");
                return new ResponseEntity(jsonParser.toJson(recipeList), HttpStatus.OK);
            } else
                return new ResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    /*@GetMapping
    public ResponseEntity getRecipe() {
        try {
            List<Recipe> recipeList;

            RecipeResponse recipes = externalRecipeAPI.getRecipe();
            if (recipes.getRecipes().isPresent()) {
                recipeList = recipes.getRecipes().get();
                recipeList.forEach(recipe -> recipe.getRecipeGoodSet().forEach(recipeGood -> recipeGood.setRecipe(recipe)));
                recipeList.forEach(recipe -> recipe.getRecipeGoodSet().forEach(recipeGood -> recipeGood.getSize().setRecipeGood(recipeGood)));
                log.info("Соединение с рецептом завершено.");
                recipeList.forEach(x -> recipeServiceImp.create(x));
                log.info("Запись в БД завершена.");
                return new ResponseEntity(jsonParser.toJson(recipeList), HttpStatus.OK);
            } else
                return new ResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }*/
}
