package com.holod.HolodOS.good;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Good {
    Integer id;
    //Наименование
    @NonNull @NotBlank @JsonProperty("name")
    String name;
    @JsonProperty("servingSize")
    ServingSize servingSize;

    //Кол-во
    @PositiveOrZero
    Integer count;
    //Измерение (шт., кг. и т.д.)
    @NonNull
    String measure;
    //Дата покупки
    LocalDate dateOfPurchase;
    //Срок годности
    LocalDate expirationDate;
}
