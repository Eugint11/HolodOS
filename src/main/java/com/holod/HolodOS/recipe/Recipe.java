package com.holod.HolodOS.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {
    @JsonIgnore
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "recipe_id_seq", sequenceName = "recipe_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_seq")
    @PrimaryKeyJoinColumn
    Integer id;
    //Наиенование
    @NonNull @NotBlank
    @JsonProperty("name")
    @Column(name = "name")
    String name;
    //Наименование товара, кол-во (наименование метрики)
    @JsonProperty("ingredients")
    @OneToMany(cascade = CascadeType.ALL, mappedBy="recipe")
    Set<RecipeGood> recipeGoodSet;
    @JsonProperty("steps")
    @Column(name = "steps")
    //Рецепт
    String[] steps;
}
