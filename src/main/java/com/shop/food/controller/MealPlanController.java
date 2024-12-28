package com.shop.food.controller;

import com.shop.food.dto.MealPlanDto;
import com.shop.food.entity.meal.MealPlan;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.iservice.MealPlanService;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;
    private final ServerUtil serverUtil;

    @Operation(summary = "Create a new MealPlan",
            description = "Creates a new MealPlan with the provided details. The date must be in yyyy-MM-dd format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "MealPlan created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealPlan.class))),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @PostMapping
    public ResponseEntity<ResponseBody> createMealPlan(
            @RequestBody @Parameter(description = "Details of the MealPlan to be created") MealPlanDto mealPlanDto) throws UnauthorizedException {
        MealPlan createdMealPlan = mealPlanService.createMealPlan(mealPlanDto);
        ResponseBody responseBody = new ResponseBody("MealPlan created successfully", ResponseBody.SUCCESS, createdMealPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @Operation(summary = "Update an existing MealPlan",
            description = "Updates an existing MealPlan by ID with the provided details. The date must be in yyyy-MM-dd format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MealPlan updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealPlan.class))),
            @ApiResponse(responseCode = "404", description = "MealPlan not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateMealPlan(
            @PathVariable @Parameter(description = "ID of the MealPlan to update") Integer id,
            @RequestBody @Parameter(description = "Updated details of the MealPlan") MealPlanDto mealPlanDto) {
        MealPlan updatedMealPlan = mealPlanService.updateMealPlan(id, mealPlanDto);
        ResponseBody responseBody = new ResponseBody("MealPlan updated successfully", ResponseBody.SUCCESS, updatedMealPlan);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Delete a MealPlan",
            description = "Deletes a MealPlan by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "MealPlan deleted successfully"),
            @ApiResponse(responseCode = "404", description = "MealPlan not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteMealPlan(
            @PathVariable @Parameter(description = "ID of the MealPlan to delete") Integer id) {
        mealPlanService.deleteMealPlan(id);
        ResponseBody responseBody = new ResponseBody("MealPlan deleted successfully", ResponseBody.SUCCESS, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    @Operation(summary = "Retrieve MealPlans by Date and Group ID",
            description = "Gets a list of MealPlans based on a specific date and group ID. The date must be in yyyy-MM-dd format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MealPlans retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealPlan.class))),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @GetMapping
    public ResponseEntity<ResponseBody> getMealPlansByDate(
            @RequestParam @Parameter(description = "Date in yyyy-MM-dd format") String date,
            @RequestParam @Parameter(description = "Group ID to filter MealPlans") Integer groupId) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByDate(date, groupId);
        ResponseBody responseBody = new ResponseBody("MealPlans retrieved successfully", ResponseBody.SUCCESS, mealPlans);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<ResponseBody> getMealPlansByGroup(
            @PathVariable @Parameter(description = "Group ID to filter MealPlans") Integer groupId) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByGroup(groupId);
        ResponseBody responseBody = new ResponseBody("MealPlans retrieved successfully", ResponseBody.SUCCESS, mealPlans);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/groups")
    public ResponseEntity<ResponseBody> getMealPlansByGroup(
            @RequestParam @Parameter(description = "Group IDs to filter MealPlans") List<Integer> groupIds)  throws UnauthorizedException{
        List<MealPlan> mealPlans = new ArrayList<>();
        for (Integer groupId : groupIds) {
            if (serverUtil.checkUserInGroup(groupId)) {
                mealPlans.addAll(mealPlanService.getMealPlansByGroup(groupId));
            }
        }
        ResponseBody responseBody = new ResponseBody("MealPlans retrieved successfully", ResponseBody.SUCCESS, mealPlans);
        return ResponseEntity.ok(responseBody);
    }
}
