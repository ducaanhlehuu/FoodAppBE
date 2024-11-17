package com.shop.food.service.iservice;

import com.shop.food.dto.MealPlanDto;
import com.shop.food.entity.meal.MealPlan;

import java.util.Date;
import java.util.List;

public interface MealPlanService {
    MealPlan createMealPlan(MealPlanDto mealPlanDto);

    MealPlan updateMealPlan(Integer id, MealPlanDto mealPlanDto);

    void deleteMealPlan(Integer id);

    List<MealPlan> getMealPlansByDate(String formattedDate, Integer groupId);
}