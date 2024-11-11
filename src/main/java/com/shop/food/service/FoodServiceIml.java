package com.shop.food.service;

import com.shop.food.dto.FoodDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.food.FoodCategory;
import com.shop.food.entity.food.MeasureUnit;
import com.shop.food.repository.*;
import com.shop.food.service.iservice.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceIml implements FoodService {
    private final FoodRepository foodRepository;
    private final MeasureUnitRepository measureUnitRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public Food createFood(FoodDto foodDto, String imageUrl) {
        Food food = Food.builder()
                .name(foodDto.getName())
                .imageUrl(imageUrl)
                .type(foodDto.getType())
                .description(foodDto.getDescription())
                .build();
        food.setMeasureUnit(this.getMeasureUnitByName(foodDto.getUnitName()));
        food.setFoodCategory(this.getFoodCategoryByAlias(foodDto.getFoodCategoryAlias()));
        food.setGroup(this.groupRepository.findById(foodDto.getGroupId()).orElse(null));
        food.setOwner(this.userRepository.findById(foodDto.getOwnerId()).orElse(null));
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(FoodDto foodDto, Integer id, String imageUrl) {
        Food exitedFood = foodRepository.findById(id).orElse(null);
        if(exitedFood==null) {
            return null;
        }
        exitedFood.setName(foodDto.getName());
        exitedFood.setDescription(foodDto.getDescription());
        exitedFood.setImageUrl((imageUrl !=null && !imageUrl.isEmpty()) ? imageUrl : exitedFood.getImageUrl());
        exitedFood.setType(foodDto.getType());
        exitedFood.setMeasureUnit(this.getMeasureUnitByName(foodDto.getUnitName()));
        exitedFood.setFoodCategory(this.getFoodCategoryByAlias(foodDto.getFoodCategoryAlias()));
        return foodRepository.save(exitedFood);
    }

    @Override
    public void deleteFood(Integer foodId) {
        foodRepository.deleteById(foodId);
    }

    @Override
    public List<Food> getAllFoodsInGroup(Integer groupId) {
        return foodRepository.getAllFoodInGroup(groupId);
    }


    @Override
    public Food getFoodById(Integer foodId) {
        return foodRepository.findById(foodId).orElse(null);
    }

    @Override
    public MeasureUnit getMeasureUnitByName(String unitName) {
        return measureUnitRepository.findByUnitName(unitName).orElse(null);
    }

    @Override
    public FoodCategory getFoodCategoryByAlias(String alias) {
        return foodCategoryRepository.findByAlias(alias).orElse(null);
    }

}
