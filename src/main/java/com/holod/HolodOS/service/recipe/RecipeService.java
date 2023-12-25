package com.holod.HolodOS.service.recipe;

import com.holod.HolodOS.recipe.Recipe;

import java.util.List;

public interface RecipeService {
    void create(Recipe recipe);

    List<Recipe> readAll();

    Recipe read(int id);

    boolean update(Recipe recipe, int id);

    boolean delete(int id);
}
