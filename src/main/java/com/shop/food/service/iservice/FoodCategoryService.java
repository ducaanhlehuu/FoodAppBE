package com.shop.food.service.iservice;


import com.shop.food.entity.food.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    FoodCategory create(FoodCategory foodCategory);

    FoodCategory getById(Integer id);

    List<FoodCategory> getAll();

    FoodCategory save(Integer id, FoodCategory foodCategory);

    boolean delete(Integer id);
}
