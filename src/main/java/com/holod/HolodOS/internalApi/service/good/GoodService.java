package com.holod.HolodOS.internalApi.service.good;

import com.holod.HolodOS.buisinesObject.good.Good;

import java.util.List;

public interface GoodService {
    void create(Good good);

    List<Good> readAll();

    Good read(int id);

    boolean update(Good client, int id);

    boolean delete(int id);
}
