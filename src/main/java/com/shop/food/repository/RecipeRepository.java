package com.shop.food.repository;

import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.meal.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r FROM Recipe r JOIN r.groups g WHERE g.id = ?1")
    List<Recipe> findRecipesByGroupId(Integer groupId);
}
