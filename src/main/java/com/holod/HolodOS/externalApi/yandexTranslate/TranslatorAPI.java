package com.holod.HolodOS.externalApi.yandexTranslate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TranslatorAPI {


    public static ResponseEntity<String> translate(String fromLng, String toLng, String textRaw){
        try {
            RestTemplate restTemplate = new RestTemplate();
            Translator translator = new Translator();
            Map<String, String> params = new HashMap<>();
                params.put("sourceLanguageCode", fromLng);
                params.put("targetLanguageCode", toLng);
                params.put("format", "PLAIN_TEXT");
                params.put("texts", textRaw);
            translator.setParams(params);
            ResponseEntity<String> responseTranslate = restTemplate.exchange(
                    translator.getUri(),
                    HttpMethod.POST,
                    translator.getHeaders(),
                    String.class
            );
            return responseTranslate;
        }
        catch (HttpClientErrorException e){
            log.error(e.getMessage());
        }
        return null;
    }
}
