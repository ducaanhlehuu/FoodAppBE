package com.shop.food.controller;

import com.shop.food.dto.MealPlanDto;
import com.shop.food.entity.meal.MealPlan;
import com.shop.food.service.iservice.MealPlanService;
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
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @Operation(summary = "Create a new MealPlan",
            description = "Creates a new MealPlan with the provided details. The date must be in yyyy-MM-dd format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "MealPlan created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealPlan.class))),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @PostMapping
    public ResponseEntity<MealPlan> createMealPlan(
            @RequestBody @Parameter(description = "Details of the MealPlan to be created") MealPlanDto mealPlanDto) {
        MealPlan createdMealPlan = mealPlanService.createMealPlan(mealPlanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMealPlan);
    }

    @Operation(summary = "Update an existing MealPlan",
            description = "Updates an existing MealPlan by ID with the provided details. The date must be in yyyy-MM-dd format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MealPlan updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealPlan.class))),
            @ApiResponse(responseCode = "404", description = "MealPlan not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> updateMealPlan(
            @PathVariable @Parameter(description = "ID of the MealPlan to update") Integer id,
            @RequestBody @Parameter(description = "Updated details of the MealPlan") MealPlanDto mealPlanDto) {
        MealPlan updatedMealPlan = mealPlanService.updateMealPlan(id, mealPlanDto);
        return ResponseEntity.ok(updatedMealPlan);
    }

    @Operation(summary = "Delete a MealPlan",
            description = "Deletes a MealPlan by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "MealPlan deleted successfully"),
            @ApiResponse(responseCode = "404", description = "MealPlan not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(
            @PathVariable @Parameter(description = "ID of the MealPlan to delete") Integer id) {
        mealPlanService.deleteMealPlan(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve MealPlans by Date and Group ID",
            description = "Gets a list of MealPlans based on a specific date and group ID. The date must be in yyyy-MM-dd format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MealPlans retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealPlan.class))),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @GetMapping
    public ResponseEntity<List<MealPlan>> getMealPlansByDate(
            @RequestParam @Parameter(description = "Date in yyyy-MM-dd format") String date,
            @RequestParam @Parameter(description = "Group ID to filter MealPlans") Integer groupId) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByDate(date, groupId);
        return ResponseEntity.ok(mealPlans);
    }
}
