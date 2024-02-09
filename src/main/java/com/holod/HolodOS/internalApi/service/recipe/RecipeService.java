package com.holod.HolodOS.internalApi.service.recipe;

import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import org.postgresql.util.PSQLException;

import java.util.Collection;
import java.util.List;

public interface RecipeService {
    void create(Recipe recipe);

    List<Recipe> readAll();

    Recipe read(int id);

    List<Recipe> read(String name,
                      Collection<String> ingredients,
                      Integer limit) throws PSQLException;

    boolean update(Recipe recipe, int id);

    boolean delete(int id);
}
