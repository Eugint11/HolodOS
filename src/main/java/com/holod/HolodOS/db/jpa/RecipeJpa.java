package com.holod.HolodOS.jpa;

import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface RecipeJpa extends JpaRepository<Recipe, Integer> {

    @Query(value =
            "select r.* "+
            "from recipe r "+
            "join recipe_good rg on rg.recipe_id = r.id "+
            "where r.id in ("+
                "select rg.recipe_id "+
                "from recipe_good rg "+
                "group by rg.recipe_id "+
                "having array_agg(string_to_array(:ingredientsLike, ','))  <@ cast(array_agg(rg.name) as text[]) "+
            "limit :limitLike"+
            ") "+
            "group by r.id, r.name, r.steps",
            nativeQuery = true)
    List<Recipe> readByParams(@Param("ingredientsLike") String ingredientsLike,
                              @Param("limitLike") int limitLike);

    @Query(value = "SELECT * " +
            "FROM Recipe r " +
            "WHERE r.name ilike CONCAT('%',:nameLike,'%') "+
            "LIMIT :limitLike",
            nativeQuery = true)
    List<Recipe> readByName(@Param("nameLike") String nameLike,
                            @Param("limitLike") int limitLike);


}
