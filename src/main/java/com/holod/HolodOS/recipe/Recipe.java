package com.holod.HolodOS.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.holod.HolodOS.good.Good;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @JsonIgnore
    Integer id;
    //Наиенование
    @NonNull @NotBlank
    @JsonProperty("name")
    String name;
    //Наименование товара, кол-во (наименование метрики)
    @JsonProperty("ingredients")
    List<Good> goods;
    @JsonProperty("steps")
    //Рецепт
    String[] steps;
}
