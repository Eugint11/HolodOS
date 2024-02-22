package com.holod.HolodOS.externalApi.yandexTranslate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.stream.Collectors;

public class Translator {
    final String uri = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    @Getter
    final String authorizationKey = "Api-Key AQVN0tiFr2xhdLi6nOisYmFvcS8WBCLs13T59Yr0";

    @Getter
    @Setter
    Map<String, String> params;


    public String getUri(){
        return uri + "?" + String.join("&",
                this
                        .getParams()
                        .keySet()
                        .stream()
                        .map(x -> x + "=" + params.get(x))
                        .collect(Collectors.joining("&"))
        );
    }

    public HttpEntity<String> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getAuthorizationKey());
        return new HttpEntity<String>(headers);
    }
}
