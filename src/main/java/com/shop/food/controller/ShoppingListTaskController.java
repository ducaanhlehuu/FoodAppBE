package com.shop.food.controller;

import com.shop.food.dto.ShoppingListDto;
import com.shop.food.dto.TaskDto;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.entity.shopping.ShoppingList;
import com.shop.food.entity.shopping.Task;
import com.shop.food.entity.user.User;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.service.iservice.ShoppingListTaskService;
import com.shop.food.service.iservice.UserService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/shopping")
@RequiredArgsConstructor
public class ShoppingListTaskController {

    private final ShoppingListTaskService shoppingListTaskService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<ResponseBody> createShoppingList(@RequestBody ShoppingListDto shoppingListDto) throws UserNotFoundException, UnauthorizedException {
        String email = ServerUtil.getAuthenticatedUserEmail();
        User currentUser = userService.getUserByEmail(email);
        shoppingListDto.setOwnerId(currentUser.getId());
        ShoppingList createdList = shoppingListTaskService.createShoppingList(shoppingListDto);
        return new ResponseEntity<>(new ResponseBody("Shopping list created successfully", "Success", createdList), HttpStatus.OK);
    }

    @PutMapping("/{listId}")
    public ResponseEntity<ResponseBody> updateShoppingList(@PathVariable Integer listId, @RequestBody ShoppingListDto updateRequest) throws ResourceNotFoundException {
        ShoppingList updatedList = shoppingListTaskService.updateShoppingList(listId, updateRequest);
        return new ResponseEntity<>(new ResponseBody("Shopping list updated successfully", "Success", updatedList), HttpStatus.OK);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<ResponseBody> deleteShoppingList(@PathVariable Integer listId) {
        shoppingListTaskService.deleteShoppingList(listId);
        return new ResponseEntity<>(new ResponseBody("Shopping list deleted successfully", "Success", null), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<ResponseBody> getShoppingListByGroupId(@PathVariable Integer groupId) {
        List<ShoppingList> shoppingLists = shoppingListTaskService.getShoppingListByGroupId(groupId);
        return new ResponseEntity<>(new ResponseBody("Shopping lists retrieved successfully", "Success", shoppingLists), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseBody> getShoppingListByUserId(@PathVariable Integer userId) {
        List<ShoppingList> shoppingLists = shoppingListTaskService.getShoppingListByUserId(userId);
        return new ResponseEntity<>(new ResponseBody("Shopping lists retrieved successfully", "Success", shoppingLists), HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<ResponseBody> createTask(@RequestParam Integer listId,@RequestBody TaskDto taskDto) throws ResourceNotFoundException {
        Task createdTask = shoppingListTaskService.createTask(taskDto, listId);
        return new ResponseEntity<>(new ResponseBody("Task created successfully", "Success", createdTask), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<ResponseBody> createTasks(@RequestParam Integer listId, @RequestBody List<TaskDto> taskDtos) throws ResourceNotFoundException {
        List<Task> createdTasks = shoppingListTaskService.createTasks(taskDtos, listId);
        return new ResponseEntity<>(new ResponseBody("Tasks created successfully", "Success", createdTasks), HttpStatus.OK);
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<ResponseBody> updateTask(@PathVariable Integer taskId, @RequestBody TaskDto taskDto) throws ResourceNotFoundException {
        Task updatedTask = shoppingListTaskService.updateTask(taskId, taskDto);
        return new ResponseEntity<>(new ResponseBody("Task updated successfully", "Success", updatedTask), HttpStatus.OK);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<ResponseBody> deleteTask(@PathVariable Integer taskId) {
        shoppingListTaskService.deleteTask(taskId);
        return new ResponseEntity<>(new ResponseBody("Task deleted successfully", "Success", null), HttpStatus.OK);
    }
}
