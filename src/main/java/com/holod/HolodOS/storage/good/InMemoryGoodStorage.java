package com.holod.HolodOS.storage.good;

import com.holod.HolodOS.good.Good;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Component
public class InMemoryGoodStorage implements GoodStorage{
    List<Good> goodsList = new ArrayList<>();
    private int lastId = 0;
    @Override
    public Optional<List<Good>> getGoods(String like) {
        List<Good> goods = goodsList.stream().filter(x -> x.getName().toUpperCase().contains(like.toUpperCase())).toList();
        return Optional.of(goods);
    }

    @Override
    public Optional<Good> addGood(Good good) {
        Good newGood = good.toBuilder().id(getLastId()).build();
        goodsList.add(newGood);
        log.info("Товар в холодильник добавлен." + newGood.toString());
        return Optional.of(newGood);
    }

    @Override
    public Optional<Good> updateGood(Good good) {
        Optional<Good> oldGood = goodsList.stream().filter(x -> x.getId().equals(good.getId())).findFirst();
        if (oldGood.isPresent()) {
            goodsList.remove(oldGood.get());
            goodsList.add(good);
            log.info("Информация о товаре в холодильнике изменилась." + good);
            return Optional.of(good);
        }
        log.info("Товар в холодильникe не найден." + good.toString());
        return Optional.empty();
    }

    @Override
    public boolean deleteGood(Integer id) {
        Optional<Good> oldGood = goodsList.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (oldGood.isPresent()) {
            goodsList.remove(oldGood.get());
            log.info("Товар из холодильника удален." + id);
            return true;
        }
        log.info("Товар в холодильникe не найден.");
        return false;
    }
    private int getLastId() {
        return lastId++;
    }
}
