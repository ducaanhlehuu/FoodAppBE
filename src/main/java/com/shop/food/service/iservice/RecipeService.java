package com.shop.food.service.iservice;

import com.shop.food.dto.RecipeDto;
import com.shop.food.entity.meal.Recipe;
import com.shop.food.exception.ResourceNotFoundException;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(RecipeDto recipeDto) throws ResourceNotFoundException;
    Recipe updateRecipe(Integer id, RecipeDto recipeDto) throws ResourceNotFoundException;
    void deleteRecipe(Integer id) throws ResourceNotFoundException;
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Integer id) throws ResourceNotFoundException;
}
