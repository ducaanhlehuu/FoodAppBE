package com.shop.food.service;

import com.shop.food.dto.RecipeDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.meal.Recipe;
import com.shop.food.entity.user.User;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.repository.FoodRepository;
import com.shop.food.repository.RecipeRepository;
import com.shop.food.repository.UserRepository;
import com.shop.food.service.iservice.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Override
    public Recipe createRecipe(RecipeDto recipeDto) throws ResourceNotFoundException {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setHtmlContent(recipeDto.getHtmlContent());

        if (foodRepository.existsById(recipeDto.getFoodId())) {
            recipe.setFood(foodRepository.findById(recipeDto.getFoodId()).get());
        } else {
            throw new ResourceNotFoundException("Food not found with ID: " + recipeDto.getFoodId());
        }

        if (userRepository.existsById(recipeDto.getAuthorId())) {
            recipe.setAuthor(User.builder().id(recipeDto.getAuthorId()).build());
        } else {
            throw new ResourceNotFoundException("Author not found with ID: " + recipeDto.getAuthorId());
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Integer id, RecipeDto recipeDto) throws ResourceNotFoundException {
        Recipe recipe = findRecipeByIdOrThrow(id);

        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setHtmlContent(recipeDto.getHtmlContent());

        if (foodRepository.existsById(recipeDto.getFoodId())) {
            recipe.setFood(foodRepository.findById(recipeDto.getFoodId()).get());
        } else {
            throw new ResourceNotFoundException("Food not found with ID: " + recipeDto.getFoodId());
        }

        if (userRepository.existsById(recipeDto.getAuthorId())) {
            recipe.setAuthor(User.builder().id(recipeDto.getAuthorId()).build());
        } else {
            throw new ResourceNotFoundException("Author not found with ID: " + recipeDto.getAuthorId());
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Integer id) throws ResourceNotFoundException {
        Recipe recipe = findRecipeByIdOrThrow(id);
        recipeRepository.delete(recipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Integer id) throws ResourceNotFoundException {
        return findRecipeByIdOrThrow(id);
    }

    /**
     * Helper method to find a Recipe by ID or throw ResourceNotFoundException.
     *
     * @param id The ID of the Recipe to find.
     * @return The found Recipe.
     * @throws ResourceNotFoundException If no Recipe is found with the given ID.
     */
    private Recipe findRecipeByIdOrThrow(Integer id) throws ResourceNotFoundException {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
    }
}
