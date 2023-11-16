package com.holod.HolodOS.recipe;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.stream.Collectors;

@Data
public class RecipeApi {
    private static final String uri = "https://low-carb-recipes.p.rapidapi.com/search";

    @Getter
    @Setter
    Map<String, String> params;

    public String setURL() {
        return uri + "?" + String.join("&",
                this
                        .getParams()
                        .keySet()
                        .stream()
                        .map(x -> x + "=" + params.get(x))
                        .collect(Collectors.joining("&"))
        );
    }

    public static HttpEntity<String> setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", "75e8a02d7emsh015ca973ec02c11p18c364jsn9f8575687982");
        headers.add("X-RapidAPI-Host", "low-carb-recipes.p.rapidapi.com");
        return new HttpEntity<String>(headers);
    }
}
