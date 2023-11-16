package com.holod.HolodOS.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class RecipeResponse {
    HttpStatus httpStatus;
    Optional<List<Recipe>> recipes;
}