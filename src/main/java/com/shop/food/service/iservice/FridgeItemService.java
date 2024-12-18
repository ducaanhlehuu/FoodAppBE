package com.shop.food.service.iservice;

import com.shop.food.dto.FridgeItemDto;
import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.exception.ResourceNotFoundException;

import java.util.List;

public interface FridgeItemService {
    FridgeItem createFridgeItem(FridgeItemDto fridgeItem) throws ResourceNotFoundException;

    FridgeItem updateFridgeItem(Integer fridgeItemId, FridgeItemDto fridgeItem) throws ResourceNotFoundException;

    void deleteFridgeItem(Integer fridgeItemId) throws ResourceNotFoundException;

    FridgeItem getFridgeItemById(Integer fridgeItemId) throws ResourceNotFoundException;

    List<FridgeItem> getFridgeItemsByGroupId(Integer groupId);

    List<FridgeItem> getFridgeItemsByFoodId(Integer foodId);

}
