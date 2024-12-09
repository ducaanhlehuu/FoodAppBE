package com.shop.food.service;

import com.shop.food.dto.FridgeItemDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.repository.FridgeItemRepository;
import com.shop.food.repository.FoodRepository;
import com.shop.food.repository.GroupRepository;
import com.shop.food.repository.UserRepository;
import com.shop.food.service.iservice.FridgeItemService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FridgeItemServiceImpl implements FridgeItemService {
    private final FridgeItemRepository fridgeItemRepository;
    private final FoodRepository foodRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public FridgeItem createFridgeItem(FridgeItemDto fridgeItemDto) throws ResourceNotFoundException {
        FridgeItem fridgeItem = mapDtoToEntity(fridgeItemDto);
        return fridgeItemRepository.save(fridgeItem);
    }

    @Override
    public FridgeItem updateFridgeItem(Integer fridgeItemId, FridgeItemDto fridgeItemDto) throws ResourceNotFoundException {
        FridgeItem existingItem = fridgeItemRepository.findById(fridgeItemId)
                .orElseThrow(() -> new ResourceNotFoundException("FridgeItem not found with ID: " + fridgeItemId));
        FridgeItem updatedItem = mapDtoToEntity(fridgeItemDto);
        updatedItem.setId(existingItem.getId());
        return fridgeItemRepository.save(updatedItem);
    }

    @Override
    public void deleteFridgeItem(Integer fridgeItemId) throws ResourceNotFoundException {
        if (!fridgeItemRepository.existsById(fridgeItemId)) {
            throw new ResourceNotFoundException("FridgeItem not found with ID: " + fridgeItemId);
        }
        fridgeItemRepository.deleteById(fridgeItemId);
    }

    @Override
    public FridgeItem getFridgeItemById(Integer fridgeItemId) throws ResourceNotFoundException {
        return fridgeItemRepository.findById(fridgeItemId)
                .orElseThrow(() -> new ResourceNotFoundException("FridgeItem not found with ID: " + fridgeItemId));
    }
    @Override
    public List<FridgeItem> getFridgeItemsByGroupId(Integer groupId) {
        return fridgeItemRepository.findByGroupId(groupId);
    }

    @Override
    public List<FridgeItem> getFridgeItemsByFoodId(Integer foodId) {
        return fridgeItemRepository.findByFoodId(foodId);
    }

    private FridgeItem mapDtoToEntity(FridgeItemDto fridgeItemDto) throws ResourceNotFoundException {
        FridgeItem fridgeItem = new FridgeItem();
        fridgeItem.setFoodName(fridgeItemDto.getFoodName());
        fridgeItem.setQuantity(fridgeItemDto.getQuantity());
        fridgeItem.setExpiredDate(new Date(new Date().getTime() + fridgeItemDto.getUseWithin() * 1000L));
        fridgeItem.setNote(fridgeItemDto.getNote());

        if (fridgeItemDto.getFoodId() != null) {
            if (foodRepository.existsById(fridgeItemDto.getFoodId())) {
                fridgeItem.setFood(foodRepository.findById(fridgeItemDto.getFoodId()).orElse(null));
            }
            else {
                throw new ResourceNotFoundException("Food not found with ID: " + fridgeItemDto.getFoodId());
            }
        }
        if (fridgeItemDto.getOwnerId() != null) {
            if (userRepository.existsById(fridgeItemDto.getOwnerId())) {
                fridgeItem.setOwner(User.builder().id(fridgeItemDto.getOwnerId()).build());
            }
            else {
                throw new ResourceNotFoundException("User not found with ID: " + fridgeItemDto.getOwnerId());
            }
        }
        return fridgeItem;
    }
}
