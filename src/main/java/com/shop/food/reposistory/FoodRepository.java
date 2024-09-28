package com.shop.food.reposistory;

import com.shop.food.entity.food.Food;
import com.shop.food.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {


    @Query("SELECT f FROM Food f  WHERE f.group.id = :groupId")
    List<Food> getAllFoodInGroup(@Param("groupId") Integer groupId);
}
