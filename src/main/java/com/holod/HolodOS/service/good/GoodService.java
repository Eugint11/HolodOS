package com.holod.HolodOS.service.good;

import com.holod.HolodOS.good.Good;

import java.util.List;

public interface GoodService {
    void create(Good good);

    List<Good> readAll();

    Good read(int id);

    boolean update(Good client, int id);

    boolean delete(int id);
}
