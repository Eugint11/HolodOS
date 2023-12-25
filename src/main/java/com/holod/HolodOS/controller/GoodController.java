package com.holod.HolodOS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holod.HolodOS.good.Good;
import com.holod.HolodOS.parsers.JsonParser;
import com.holod.HolodOS.service.good.GoodServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/goods")
public class GoodController {
    private JsonParser<Good> jsonParser = new JsonParser();
    @Autowired
    private final GoodServiceImp goodServiceImp;

    @Autowired
    public GoodController(GoodServiceImp goodServiceImp) {
        this.goodServiceImp = goodServiceImp;
    }

    @GetMapping
    public ResponseEntity getGoods() {
        try {
            List<Good> goods = goodServiceImp.readAll();
            log.info(goods.toString() + ": " + goods.size());
            return goods.get(0) != null ?
                    new ResponseEntity(jsonParser.toJson(goods), HttpStatus.OK) :
                    new ResponseEntity("Товар в холодильникe не найден.", HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            log.info(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = {"nameLike"})
    public ResponseEntity getGoods(@Valid
                                   @RequestParam(value = "nameLike") String nameLike) {
        try {
            List<Good> goods = goodServiceImp.readByName(nameLike);
            log.info(goods.toString() + ": " + goods.size());
            return !goods.isEmpty() ?
                    new ResponseEntity(jsonParser.toJson(goods), HttpStatus.OK) :
                    new ResponseEntity("Товар в холодильникe не найден.", HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            log.info(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    ResponseEntity addGood(@Valid @RequestBody Good good) {
        try {
            goodServiceImp.create(good);
            return new ResponseEntity(HttpStatus.OK, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    ResponseEntity updateGood(@Valid @RequestBody Good good) {
        try {
            goodServiceImp.update(good, good.getId());
            return new ResponseEntity(HttpStatus.OK, HttpStatus.OK);/*:
                    new ResponseEntity(gson.toJson("Товар в холодильникe не найден."), HttpStatus.NOT_FOUND);*/
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    ResponseEntity deleteGood(@Positive @RequestParam Integer id) {
        try {
            boolean deleteGood = goodServiceImp.delete(id);
            //goodStorage.deleteGood(id);
            return deleteGood ?
                    new ResponseEntity(HttpStatus.OK, HttpStatus.OK) :
                    new ResponseEntity("Товар в холодильникe не найден.", HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }
}
