package com.holod.HolodOS.good;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServingSize {
    @JsonProperty("units")
    String units;
    @JsonProperty("desc")
    String desc;
    @JsonProperty("qty")
    Float qty;
    @JsonProperty("grams")
    Float grams;
    @JsonProperty("scale")
    Float scale;
}
