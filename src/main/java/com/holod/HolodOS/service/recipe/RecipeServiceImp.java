package com.holod.HolodOS.service.recipe;

import com.holod.HolodOS.good.Good;
import com.holod.HolodOS.jpa.GoodJpa;
import com.holod.HolodOS.jpa.RecipeJpa;
import com.holod.HolodOS.recipe.Recipe;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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


    public List<Recipe> readByName(String name,
                                   Integer limit) throws PSQLException{
        List<Recipe> recipes;
        recipes = recipeJpa.readByName(name, limit);
        log.info(recipes.toString());
        return recipes;
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

