package com.holod.HolodOS.buisinesObject.good;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.datatype.jsr310.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "good")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Good {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "good_id_seq", sequenceName = "good_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "good_id_seq")
    @PrimaryKeyJoinColumn
    Integer id;
    //Наименование
    @NonNull @NotBlank
    @JsonProperty("name")
    @Column(name = "name")
    String name;

    @JsonProperty("units")
    @NonNull
    String units;
    @JsonProperty("qty")
    @PositiveOrZero @NonNull
    Float qty;
    @JsonProperty("desc")
    String description;
    @JsonProperty("grams")
    @PositiveOrZero @NonNull
    Float grams;
    @JsonProperty("scale")
    @PositiveOrZero
    Float scale;
    @JsonProperty("dateOfPurchase")
    //Дата покупки
    LocalDateTime dateOfPurchase;
    @JsonProperty("expirationDate")
    //Срок годности
    LocalDateTime expirationDate;
}
