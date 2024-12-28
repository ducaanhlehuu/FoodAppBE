package com.shop.food.service;

import com.shop.food.dto.RecipeDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.meal.Recipe;
import com.shop.food.entity.user.GroupRecipe;
import com.shop.food.entity.user.User;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.repository.*;
import com.shop.food.service.iservice.RecipeService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final GroupRecipeRepository groupRecipeRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final ServerUtil serverUtil;

    @Override
    public Recipe createRecipe(RecipeDto recipeDto) throws ResourceNotFoundException, UnauthorizedException {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setHtmlContent(recipeDto.getHtmlContent());
        recipe.setAuthor(serverUtil.getCurrentUser());
        recipe.setRawMaterialIds(recipeDto.getRawMaterialIds());
        if (foodRepository.existsById(recipeDto.getFoodId())) {
            Food food= foodRepository.findById(recipeDto.getFoodId()).get();
            recipe.getGroups().add(food.getGroup());
            recipe.setFood(food);
        } else {
            throw new ResourceNotFoundException("Food not found with ID: " + recipeDto.getFoodId());
        }
        recipeRepository.save(recipe);
        GroupRecipe groupRecipe = groupRecipeRepository.findByGroupIdAndRecipeId(recipe.getFood().getGroupId(),recipe.getId());
        groupRecipe.setTimeStamp(LocalDateTime.now());
        groupRecipeRepository.save(groupRecipe);
        return recipe;
    }

    @Override
    public Recipe updateRecipe(Integer id, RecipeDto recipeDto) throws ResourceNotFoundException {
        Recipe recipe = findRecipeByIdOrThrow(id);

        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setHtmlContent(recipeDto.getHtmlContent());
        recipe.setRawMaterialIds(recipeDto.getRawMaterialIds());

        if (foodRepository.existsById(recipeDto.getFoodId())) {
            recipe.setFood(foodRepository.findById(recipeDto.getFoodId()).get());
        } else {
            throw new ResourceNotFoundException("Food not found with ID: " + recipeDto.getFoodId());
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

    @Override
    public void shareRecipeToGroup(Integer recipeId, Integer groupId) throws UnauthorizedException, ResourceNotFoundException {
        boolean valid = serverUtil.checkUserInGroup(groupId);
        if (valid) {
            Recipe recipe = this.getRecipeById(recipeId);
            if (recipe !=null) {
                if (groupRecipeRepository.existsByGroupIdAndRecipeId(groupId,recipeId)){
                    throw new IllegalArgumentException("This group have been shared this recipe before");
                }
                groupRecipeRepository.save(GroupRecipe.builder().groupId(groupId).recipeId(recipeId).timeStamp(LocalDateTime.now()).build());
            }
        }
    }

    @Override
    public List<Recipe> getRecipesInGroup(Integer groupId) {
       try {
           return recipeRepository.findRecipesByGroupId(groupId);
       } catch (Exception e) {
           return new ArrayList<>();
       }
    }

    @Override
    public List<Recipe> getRecipesByUser() throws UnauthorizedException {
        User currentUser = serverUtil.getCurrentUser();
        List<Recipe> recipes = new ArrayList<>();
        currentUser.getGroupIds().forEach(
                groupId -> recipes.addAll(recipeRepository.findRecipesByGroupId(groupId))
        );
        return recipes;
    }

    @Override
    public void removeRecipeOfGroup(Integer recipeId, Integer groupId) throws UnauthorizedException, ResourceNotFoundException {
        boolean valid = serverUtil.checkUserInGroup(groupId);
        if (valid) {
            Recipe recipe = this.getRecipeById(recipeId);
            if (recipe !=null) {
                GroupRecipe groupRecipe = groupRecipeRepository.findByGroupIdAndRecipeId(groupId, recipeId);
                if (groupRecipe == null) {
                    throw new IllegalArgumentException("This group is not have this recipe now");
                }
                else {
                    groupRecipeRepository.delete(groupRecipe);
                }
            }
        }
    }
}
