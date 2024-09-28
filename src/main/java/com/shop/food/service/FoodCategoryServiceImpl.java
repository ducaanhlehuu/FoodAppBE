package com.shop.food.service;

import com.shop.food.entity.food.FoodCategory;
import com.shop.food.reposistory.FoodCategoryRepository;
import com.shop.food.service.iservice.FoodCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    public FoodCategory create(FoodCategory foodCategory) {
        return foodCategoryRepository.save(foodCategory);
    }

    @Override
    public FoodCategory getById(Integer id) {
        return foodCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FoodCategory not found with id: " + id));
    }

    @Override
    public List<FoodCategory> getAll() {
        return foodCategoryRepository.findAll();
    }

    @Override
    public FoodCategory save(Integer id, FoodCategory foodCategory) {
        if (!foodCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("FoodCategory not found with id: " + id);
        }
        foodCategory.setId(id);
        return foodCategoryRepository.save(foodCategory);
    }

    @Override
    public boolean delete(Integer id) {
        if (!foodCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("FoodCategory not found with id: " + id);
        }
        foodCategoryRepository.deleteById(id);
        return true;
    }
}
