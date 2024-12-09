package com.shop.food.repository;

import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.meal.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Integer> {

    @Query("SELECT m FROM MealPlan m WHERE FUNCTION('DATE_FORMAT', m.timeStamp, '%Y-%m-%d') = :date AND m.food.group.id = :groupId")
    List<MealPlan> findByDate(@Param("date") String date, @Param("groupId") Integer groupId);

    @Query("SELECT m FROM MealPlan m WHERE  m.food.group.id = :groupId")
    List<MealPlan> findByGroup(@Param("groupId") Integer groupId);
}
