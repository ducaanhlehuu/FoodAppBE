package com.shop.food.controller;

import com.shop.food.dto.RecipeDto;
import com.shop.food.entity.meal.Recipe;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.iservice.RecipeService;
import com.shop.food.util.ServerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "Create a new Recipe",
            description = "Creates a new Recipe with the provided details.")
    @PostMapping
    public ResponseEntity<ResponseBody> createRecipe(
            @RequestBody @Parameter(description = "Details of the Recipe to be created") RecipeDto recipeDto) throws UnauthorizedException, ResourceNotFoundException {
        try {
            Recipe recipe = recipeService.createRecipe(recipeDto);
            ResponseBody response = new ResponseBody("Recipe created successfully", ResponseBody.SUCCESS, recipe);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseBody response = new ResponseBody(e.getMessage(), ResponseBody.SERVER_ERROR, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Update an existing Recipe",
            description = "Updates an existing Recipe by ID with the provided details.")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateRecipe(
            @PathVariable @Parameter(description = "ID of the Recipe to update") Integer id,
            @RequestBody @Parameter(description = "Updated details of the Recipe") RecipeDto recipeDto) {
        try {
            Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDto);
            ResponseBody response = new ResponseBody("Recipe updated successfully", ResponseBody.SUCCESS, updatedRecipe);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ResponseBody response = new ResponseBody(e.getMessage(), ResponseBody.NOT_FOUND, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Delete a Recipe",
            description = "Deletes a Recipe by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteRecipe(
            @PathVariable @Parameter(description = "ID of the Recipe to delete") Integer id) {
        try {
            recipeService.deleteRecipe(id);
            ResponseBody response = new ResponseBody("Recipe deleted successfully", ResponseBody.DELETED, null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (ResourceNotFoundException e) {
            ResponseBody response = new ResponseBody(e.getMessage(), ResponseBody.NOT_FOUND, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Retrieve Recipes by Group Id",
            description = "Gets a list of ecipes.")
    @GetMapping("group/{groupId}")
    public ResponseEntity<ResponseBody> getAllRecipes(@PathVariable("groupId") Integer groupId) {
        List<Recipe> recipes = recipeService.getRecipesInGroup(groupId);
        ResponseBody response = new ResponseBody("Recipes retrieved successfully", ResponseBody.SUCCESS, recipes);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Retrieve Recipes of User",
            description = "Gets a list of Recipes.")
    @GetMapping("")
    public ResponseEntity<ResponseBody> getMyRecipes() throws UnauthorizedException {
        List<Recipe> recipes = recipeService.getRecipesByUser();
        ResponseBody response = new ResponseBody("Recipes retrieved successfully", ResponseBody.SUCCESS, recipes);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve a Recipe by ID",
            description = "Gets the details of a Recipe by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> getRecipeById(
            @PathVariable @Parameter(description = "ID of the Recipe to retrieve") Integer id) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            ResponseBody response = new ResponseBody("Recipe retrieved successfully", ResponseBody.SUCCESS, recipe);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ResponseBody response = new ResponseBody(e.getMessage(), ResponseBody.NOT_FOUND, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Share a Recipe to other Group",
            description = "Gets the details of a Recipe by its ID.")
    @PostMapping("/share/{recipeId}/{groupId}")
    public ResponseEntity<ResponseBody> shareRecipe(
            @PathVariable("recipeId") @Parameter(description = "ID of the Recipe to retrieve") Integer recipeId,
            @PathVariable("groupId") @Parameter(description = "ID of the Group") Integer groupId ) throws UnauthorizedException, ResourceNotFoundException {
        try {
            recipeService.shareRecipeToGroup(recipeId, groupId);
            ResponseBody response = new ResponseBody("Recipe share successfully", ResponseBody.SUCCESS, "");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody(e.getMessage(), ResponseBody.SERVER_ERROR, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
