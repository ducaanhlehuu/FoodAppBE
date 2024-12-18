package com.shop.food.repository;

import com.shop.food.entity.meal.Recipe;
import com.shop.food.entity.user.GroupRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRecipeRepository extends JpaRepository<GroupRecipe, Integer> {

    public List<GroupRecipe> findByGroupId(Integer groupId);
    boolean existsByGroupIdAndRecipeId(Integer groupId, Integer recipeId);
    public GroupRecipe findByGroupIdAndRecipeId(Integer groupId, Integer recipeId);
}
