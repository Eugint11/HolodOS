package com.holod.HolodOS.jpa;

import com.holod.HolodOS.good.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodJpa extends JpaRepository<Good, Integer> {
    @Query(value = "SELECT g FROM Good g WHERE g.name like CONCAT('%',:nameLike,'%')")
    List<Good> readByName(@Param("nameLike") String nameLike);
}
