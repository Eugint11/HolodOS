package com.holod.HolodOS.internalApi.service.good;

import com.holod.HolodOS.buisinesObject.good.Good;
import com.holod.HolodOS.jpa.GoodJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImp implements GoodService {
    @Autowired
    private final GoodJpa goodJpa;

    @Autowired
    public GoodServiceImp(GoodJpa goodJpa) {
        this.goodJpa = goodJpa;
    }

    @Override
    public void create(Good good) {
        goodJpa.save(good);
    }

    @Override
    public List<Good> readAll() {
        return goodJpa.findAll();
    }

    @Override
    public Good read(int id) {
        return goodJpa.getOne(id);
    }

    public List<Good> readByName(String name) {
        return goodJpa.readByName(name);
    }

    @Override
    public boolean update(Good good, int id) {
        if (goodJpa.existsById(id)) {
            Good newGood = good.toBuilder().id(id).build();
            goodJpa.save(newGood);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        if (goodJpa.existsById(id)) {
            goodJpa.deleteById(id);
            return true;
        }
        return false;
    }
}

