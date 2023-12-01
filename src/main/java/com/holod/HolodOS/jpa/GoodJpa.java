package com.holod.HolodOS.jpa;

import com.holod.HolodOS.good.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoodJpa extends JpaRepository<Good, Integer> {
    /*Доработать подбор нескольких записей (jakarta.persistence.NonUniqueResultException: query did not return a unique result: 2)
    Запрос работает: @Query(value = "SELECT g FROM Good g WHERE g.name like %:nameLike%")
     */
    @Query(value = "SELECT g FROM Good g WHERE g.name = :nameLike")
    Good readByName(@Param("nameLike") String nameLike);


}
