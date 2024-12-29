package com.shop.food.service.iservice;

import com.shop.food.dto.ShoppingListDto;
import com.shop.food.dto.TaskDto;
import com.shop.food.entity.shopping.ShoppingList;
import com.shop.food.entity.shopping.Task;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UserNotFoundException;

import java.util.List;

public interface ShoppingListTaskService {
    ShoppingList createShoppingList(ShoppingListDto shoppingListDto) throws UserNotFoundException;
    ShoppingList updateShoppingList(Integer listId, ShoppingListDto updateRequest) throws ResourceNotFoundException;
    void deleteShoppingList(Integer listId);
    List<ShoppingList> getShoppingListByGroupId(Integer groupId);
    List<ShoppingList> getShoppingListByUserId(Integer userId);
    ShoppingList getShoppingList(Integer listId) throws ResourceNotFoundException;

    Task createTask(TaskDto taskDto, Integer listId) throws ResourceNotFoundException;
    List<Task> createTasks(List<TaskDto> taskDtos, Integer listId) throws ResourceNotFoundException;
    Task updateTask(Integer taskId, TaskDto taskDto) throws ResourceNotFoundException;
    void deleteTask(Integer taskId);
    
    List<ShoppingList> getReportOfUser(Integer userId, Integer day);
}
