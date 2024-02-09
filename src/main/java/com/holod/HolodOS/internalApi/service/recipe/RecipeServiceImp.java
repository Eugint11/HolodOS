package com.holod.HolodOS.internalApi.service.recipe;

import com.holod.HolodOS.jpa.RecipeJpa;
import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeServiceImp implements RecipeService {
    @Autowired
    private final RecipeJpa recipeJpa;

    @Autowired
    public RecipeServiceImp(RecipeJpa recipeJpa) {
        this.recipeJpa = recipeJpa;
    }

    @Override
    public void create(Recipe recipe) {
        recipeJpa.save(recipe);
    }

    @Override
    public List<Recipe> readAll() {
        return recipeJpa.findAll();
    }

    @Override
    public Recipe read(int id) {
        return recipeJpa.getOne(id);
    }

    @Override
    public List<Recipe> read(String name,
                             Collection<String> ingredients,
                             Integer limit) throws PSQLException {
        List<Recipe> recipes;
        if(!name.toLowerCase().equals("null")){
            recipes = recipeJpa.readByName(name, limit);
            log.info(recipes.toString());
            return recipes;
        }
        if(!ingredients.equals("null")){
            log.info(String.join(",",ingredients));
            recipes = recipeJpa.readByParams(String.join(",",ingredients), limit);
            log.info(recipes.toString());
            return recipes;
        }
        return new ArrayList<>();
    }


    @Override
    public boolean update(Recipe recipe, int id) {
        if (recipeJpa.existsById(id)) {
            Recipe newRecipe = recipe.toBuilder().id(id).build();
            recipeJpa.save(newRecipe);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        if (recipeJpa.existsById(id)) {
            recipeJpa.deleteById(id);
            return true;
        }
        return false;
    }
}

