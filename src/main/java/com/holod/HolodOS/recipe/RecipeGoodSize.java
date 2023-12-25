package com.holod.HolodOS.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_good_size")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RecipeGoodSize {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "recipe_good_size_id_seq", sequenceName = "recipe_good_size_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_good_size_id_seq")
    @PrimaryKeyJoinColumn
    @JsonIgnore
    Integer id;
    @JsonProperty("units")
    @Column(name = "units")
    @NonNull
    String units;
    @JsonProperty("qty")
    @Column(name = "qty")
    @PositiveOrZero @NonNull
    Float qty;
    @JsonProperty("desc")
    @Column(name = "description")
    String description;
    @JsonProperty("grams")
    @Column(name = "grams")
    @PositiveOrZero @NonNull
    Float grams;
    @JsonProperty("scale")
    @Column(name = "scale")
    @PositiveOrZero
    Float scale;
    @Column(name = "date_of_purchase")
    @JsonProperty("dateOfPurchase")
    //Дата покупки
    LocalDateTime dateOfPurchase;
    @JsonProperty("expirationDate")
    @Column(name = "expiration_date")
    //Срок годности
    LocalDateTime expirationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    RecipeGood recipeGood;

    public void setRecipeGood(RecipeGood recipeGood){
        this.recipeGood = recipeGood;
    }

}
