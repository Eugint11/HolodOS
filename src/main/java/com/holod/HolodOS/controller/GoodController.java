package com.holod.HolodOS.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holod.HolodOS.good.Good;
import com.holod.HolodOS.recipe.Recipe;
import com.holod.HolodOS.storage.good.InMemoryGoodStorage;
import com.holod.HolodOS.typeAdapter.LocalDateTypeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(path = "/goods")
public class GoodController {
    InMemoryGoodStorage goodStorage = new InMemoryGoodStorage();
    @GetMapping
    public ResponseEntity getGoods(@Valid @RequestParam String nameLike){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try{
            Optional<List<Good>> goods = goodStorage.getGoods(nameLike);
            return goods.isPresent()?
                    new ResponseEntity(gson.toJson(goods.get()), HttpStatus.OK):
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);
        }
        catch (ValidationException e){
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    ResponseEntity addGood(@Valid @RequestBody Good good){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try{
            Optional<Good> optionalGood = goodStorage.addGood(good);
            return optionalGood.isPresent()?
                    new ResponseEntity(gson.toJson(optionalGood.get()), HttpStatus.OK):
                    new ResponseEntity(gson.toJson("Внутренняя ошибка."), HttpStatus.GATEWAY_TIMEOUT);
        }
        catch (ValidationException e){
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping
    ResponseEntity updateGood(@Valid @RequestBody Good good){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try{
            Optional<Good> optionalGood = goodStorage.updateGood(good);
            return optionalGood.isPresent()?
                    new ResponseEntity(gson.toJson(optionalGood.get()), HttpStatus.OK):
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);
        }
        catch (ValidationException e){
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping
    ResponseEntity deleteGood(@Positive @RequestParam Integer id){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        try{
            boolean deleteGood = goodStorage.deleteGood(id);
            return deleteGood?
                    new ResponseEntity(gson.toJson(deleteGood), HttpStatus.OK):
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);
        }
        catch (ValidationException e){
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
