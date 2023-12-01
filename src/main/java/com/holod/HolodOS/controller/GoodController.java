package com.holod.HolodOS.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holod.HolodOS.good.Good;
import com.holod.HolodOS.service.good.GoodServiceImp;
import com.holod.HolodOS.typeAdapter.LocalDateTypeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/goods")
public class GoodController {
    private Gson gson;
    @Autowired
    private final GoodServiceImp goodServiceImp;

    @Autowired
    public GoodController(GoodServiceImp goodServiceImp) {
        this.goodServiceImp = goodServiceImp;
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter())
                .create();
    }

    @GetMapping
    public ResponseEntity getGoods(@Valid @RequestParam String nameLike) {
        try {
            List<Good> goods = goodServiceImp.readByName(nameLike);
            log.info(goods.toString() + ": " + goods.size());
            return goods.get(0) != null ?
                    new ResponseEntity(gson.toJson(goods), HttpStatus.OK) :
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    ResponseEntity addGood(@Valid @RequestBody Good good) {
        try {
            goodServiceImp.create(good);
            return new ResponseEntity(gson.toJson(""), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    ResponseEntity updateGood(@Valid @RequestBody Good good) {
        try {
            goodServiceImp.update(good, good.getId());
            return new ResponseEntity(gson.toJson(HttpStatus.OK), HttpStatus.OK);/*:
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);*/
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    ResponseEntity deleteGood(@Positive @RequestParam Integer id) {
        try {
            boolean deleteGood = goodServiceImp.delete(id);
            //goodStorage.deleteGood(id);
            return deleteGood ?
                    new ResponseEntity(gson.toJson(deleteGood), HttpStatus.OK) :
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity(gson.toJson(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
