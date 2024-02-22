package com.holod.HolodOS.externalApi.yandexTranslate;

import com.holod.HolodOS.buisinesObject.recipe.Recipe;
import com.holod.HolodOS.externalApi.parsers.JsonParser;
import com.holod.HolodOS.externalApi.parsers.JsonRecipeParcer;
import com.holod.HolodOS.internalApi.controller.RecipeController;
import com.holod.HolodOS.internalApi.service.recipe.RecipeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@Slf4j
class TranslatorTest {

    @Test
    void translate() {
        log.info(TranslatorAPI.translate("en", "ru", "test").getBody());
        assertNotNull(TranslatorAPI.translate("en", "ru", "test"));
    }

}