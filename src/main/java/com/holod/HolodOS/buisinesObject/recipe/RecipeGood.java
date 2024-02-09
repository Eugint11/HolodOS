package com.holod.HolodOS.buisinesObject.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_good")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "id", "recipe"})
public class RecipeGood {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "recipe_good_id_seq", sequenceName = "recipe_good_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_good_id_seq")
    @PrimaryKeyJoinColumn
    Integer id;
    //Наименование
    @NonNull @NotBlank
    @JsonProperty("name")
    @Column(name = "name")
    String name;

    @JsonProperty("servingSize")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipeGood")
    RecipeGoodSize size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Recipe recipe;

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

}
