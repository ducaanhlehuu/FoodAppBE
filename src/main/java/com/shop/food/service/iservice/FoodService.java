package com.shop.food.service.iservice;

import com.shop.food.dto.FoodDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.food.FoodCategory;
import com.shop.food.entity.food.MeasureUnit;

import java.util.List;

public interface FoodService {
    Food createFood(FoodDto food, String imageUrl);
    Food updateFood(FoodDto food, Integer id, String imageUrl);
    void deleteFood(Integer foodId);
    List<Food> getAllFoodsInGroup(Integer groupId);
    Food getFoodById(Integer foodId);
    MeasureUnit getMeasureUnitByName(String unitName);
    FoodCategory getFoodCategoryByAlias(String alias);
}