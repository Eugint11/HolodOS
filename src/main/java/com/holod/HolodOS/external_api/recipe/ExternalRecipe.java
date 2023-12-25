package com.holod.HolodOS.external_api.recipe;

import com.holod.HolodOS.recipe.Recipe;
import com.holod.HolodOS.recipe.RecipeResponse;

import java.util.Map;

public interface ExternalRecipe {
    RecipeResponse getRecipe(Map<String, String> params);

    RecipeResponse getRecipe();

    RecipeResponse addRecipe(Recipe recipe);

    RecipeResponse updateRecipe(Recipe recipe);

    boolean deleteRecipe(Integer id);
}
