package com.holod.HolodOS.jpa;

import com.holod.HolodOS.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeJpa extends JpaRepository<Recipe, Integer> {

    @Query(value = "SELECT * " +
            "FROM Recipe r " +
            "JOIN Recipe_good rg on r.id = rg.recipe_id AND rg.name in (:ingredientsLike)" +
            "WHERE r.name ilike CONCAT('%',:nameLike,'%') " +
            "LIMIT :limitLike",
            nativeQuery = true)
    List<Recipe> readByParams(@Param("nameLike") String nameLike,
                              @Param("ingredientsLike") String ingredientsLike,
                              @Param("limitLike") int limitLike);

    @Query(value = "SELECT * " +
            "FROM Recipe r " +
            "WHERE r.name ilike CONCAT('%',:nameLike,'%') "+
            "LIMIT :limitLike",
            nativeQuery = true)
    List<Recipe> readByName(@Param("nameLike") String nameLike,
                            @Param("limitLike") int limitLike);


}
