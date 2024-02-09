package com.holod.HolodOS.externalApi.lowCarbRecipes.recipe;

import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import com.holod.HolodOS.buisinesObject.recipe.RecipeResponse;

import java.util.Map;

public interface ExternalRecipe {
    RecipeResponse getRecipe(Map<String, String> params);

    RecipeResponse getRecipe();

    RecipeResponse addRecipe(Recipe recipe);

    RecipeResponse updateRecipe(Recipe recipe);

    boolean deleteRecipe(Integer id);
}
