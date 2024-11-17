package com.shop.food.controller;

import com.shop.food.dto.RecipeDto;
import com.shop.food.entity.meal.Recipe;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.service.iservice.RecipeService;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(
            @RequestBody @Parameter(description = "Details of the Recipe to be created") RecipeDto recipeDto) throws ResourceNotFoundException {
        Recipe createdRecipe = recipeService.createRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }
    //-----------------------------------
    @Operation(summary = "Update an existing Recipe",
            description = "Updates an existing Recipe by ID with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable @Parameter(description = "ID of the Recipe to update") Integer id,
            @RequestBody @Parameter(description = "Updated details of the Recipe") RecipeDto recipeDto) throws ResourceNotFoundException {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }
    //-----------------------------------
    @Operation(summary = "Delete a Recipe",
            description = "Deletes a Recipe by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable @Parameter(description = "ID of the Recipe to delete") Integer id) throws ResourceNotFoundException {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
    //-----------------------------------
    @Operation(summary = "Retrieve all Recipes",
            description = "Gets a list of all Recipes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
    })
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
   //-----------------------------------
    @Operation(summary = "Retrieve a Recipe by ID",
            description = "Gets the details of a Recipe by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(
            @PathVariable @Parameter(description = "ID of the Recipe to retrieve") Integer id) throws ResourceNotFoundException {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }
}
