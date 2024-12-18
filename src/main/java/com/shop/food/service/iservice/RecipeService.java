package com.shop.food.service.iservice;

import com.shop.food.dto.RecipeDto;
import com.shop.food.entity.meal.Recipe;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UnauthorizedException;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(RecipeDto recipeDto) throws ResourceNotFoundException, UnauthorizedException;
    Recipe updateRecipe(Integer id, RecipeDto recipeDto) throws ResourceNotFoundException;
    void deleteRecipe(Integer id) throws ResourceNotFoundException;
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Integer id) throws ResourceNotFoundException;
    void shareRecipeToGroup(Integer recipeId, Integer groupId) throws UnauthorizedException, ResourceNotFoundException;
    List<Recipe> getRecipesInGroup(Integer groupId);
    List<Recipe> getRecipesByUser() throws UnauthorizedException;
}
