package com.holod.HolodOS.storage.recipe;

import com.holod.HolodOS.recipe.RecipeResponse;
import com.holod.HolodOS.recipe.Recipe;

import java.util.Map;

public interface RecipeStorage {
    RecipeResponse getRecipe(Map<String, String> params);

    RecipeResponse addRecipe(Recipe recipe);

    RecipeResponse updateRecipe(Recipe recipe);

    boolean deleteRecipe(Integer id);
}
