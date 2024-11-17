package com.shop.food.repository;

import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.meal.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
