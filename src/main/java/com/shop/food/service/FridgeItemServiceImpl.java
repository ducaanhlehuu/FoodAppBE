package com.shop.food.service;

import com.shop.food.dto.FridgeItemDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.exception.UserNotFoundException;
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
    private final ServerUtil serverUtil;

    @Override
    public FridgeItem createFridgeItem(FridgeItemDto fridgeItemDto) throws ResourceNotFoundException, UnauthorizedException {
        FridgeItem fridgeItem = mapDtoToEntity(fridgeItemDto);
        fridgeItem.setOwner(serverUtil.getCurrentUser());
        fridgeItem.setStatus(FridgeItem.STATUS_WAITING);
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
        FridgeItem fridgeItem = getFridgeItemById(fridgeItemId);
        fridgeItem.setStatus(FridgeItem.STATUS_CANCELED);
        fridgeItemRepository.save(fridgeItem);
    }

    @Override
    public FridgeItem getFridgeItemById(Integer fridgeItemId) throws ResourceNotFoundException {
        FridgeItem fridgeItem = fridgeItemRepository.findById(fridgeItemId)
                .orElseThrow(() -> new ResourceNotFoundException("FridgeItem not found with ID: " + fridgeItemId));
        updateStatus(fridgeItem);
        fridgeItemRepository.save(fridgeItem);
        return fridgeItem;
    }
    @Override
    public List<FridgeItem> getFridgeItemsByGroupId(Integer groupId) {
        List<FridgeItem> fridgeItems = fridgeItemRepository.findByGroupId(groupId);
        for (FridgeItem fridgeItem: fridgeItems) {
            updateStatus(fridgeItem);
        }
        return filterFridgeItem(fridgeItemRepository.saveAll(fridgeItems));
    }

    @Override
    public List<FridgeItem> getFridgeItemsByFoodId(Integer foodId) {
        List<FridgeItem> fridgeItems = fridgeItemRepository.findByFoodId(foodId);
        for (FridgeItem fridgeItem: fridgeItems) {
            updateStatus(fridgeItem);
        }
        return filterFridgeItem(fridgeItemRepository.saveAll(fridgeItems));
    }

    @Override
    public FridgeItem updateStatus(Integer fridgeItemId, String newStatus) throws ResourceNotFoundException {
        if (!FridgeItem.getAllStatus().contains(newStatus)) {
            throw new IllegalArgumentException("Status not valid: " + newStatus);
        }
        if (newStatus.equals(FridgeItem.STATUS_CANCELED))
            throw new IllegalArgumentException("This fridge item have been cancelled so cannot update anymore");
        FridgeItem fridgeItem = getFridgeItemById(fridgeItemId);

        if (fridgeItem.getStatus().equals(FridgeItem.STATUS_WAITING)) {
            if (!newStatus.equals(FridgeItem.STATUS_EXPIRED)) {
                fridgeItem.setStatus(newStatus);
            }
        } else {
            fridgeItem.setStatus(newStatus);
        }
        return fridgeItemRepository.save(fridgeItem);
    }

    @Override
    public List<FridgeItem> getReportOfUser(Integer userId, Integer day) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new UserNotFoundException("Not found user");
        List<FridgeItem> fridgeItems = new ArrayList<>();
        for (Integer groupId: user.getGroupIds()) {
            fridgeItems.addAll(getFridgeItemsByGroupId(groupId));
        }

        return filterFridgeItem(fridgeItems);
    }

    private FridgeItem mapDtoToEntity(FridgeItemDto fridgeItemDto) throws ResourceNotFoundException {
        FridgeItem fridgeItem = new FridgeItem();
        fridgeItem.setFoodName(fridgeItemDto.getFoodName());
        fridgeItem.setQuantity(fridgeItemDto.getQuantity());
        if (fridgeItemDto.getUseWithin()!= null && fridgeItemDto.getUseWithin() != 0) {
            fridgeItem.setExpiredDate(new Date(new Date().getTime() + fridgeItemDto.getUseWithin() * 1000L * 60));
        }
        fridgeItem.setNote(fridgeItemDto.getNote());

        if (fridgeItemDto.getFoodId() != null) {
            if (foodRepository.existsById(fridgeItemDto.getFoodId())) {
                fridgeItem.setFood(foodRepository.findById(fridgeItemDto.getFoodId()).orElse(null));
            }
            else {
                throw new ResourceNotFoundException("Food not found with ID: " + fridgeItemDto.getFoodId());
            }
        }
        return fridgeItem;
    }

    private void updateStatus(FridgeItem fridgeItem) {
        Date now = new Date();
        if (fridgeItem.getExpiredDate().getTime() < now.getTime()) {
           if (fridgeItem.getStatus().equals(FridgeItem.STATUS_WAITING)) {
               fridgeItem.setStatus(FridgeItem.STATUS_EXPIRED);
           }
        } else {
            if (fridgeItem.getStatus().equals(FridgeItem.STATUS_EXPIRED)) {
                fridgeItem.setStatus(FridgeItem.STATUS_WAITING);
            }
        }
    }

    private List<FridgeItem> filterFridgeItem(List<FridgeItem> fridgeItems) {
        return fridgeItems.stream().filter(item-> !item.getStatus().equals(FridgeItem.STATUS_CANCELED)).toList();
    }

}
