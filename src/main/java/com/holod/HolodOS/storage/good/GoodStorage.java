package com.holod.HolodOS.storage.good;

import com.holod.HolodOS.good.Good;
import com.holod.HolodOS.recipe.Recipe;

import java.util.List;
import java.util.Optional;

public interface GoodStorage {
    Optional<List<Good>> getGoods(String like);
    Optional<Good> addGood(Good good);
    Optional<Good> updateGood(Good good);
    boolean deleteGood(Integer id);
}
