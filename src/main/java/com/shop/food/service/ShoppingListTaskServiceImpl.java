package com.shop.food.service;

import com.shop.food.dto.ShoppingListDto;
import com.shop.food.dto.TaskDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.shopping.ShoppingList;
import com.shop.food.entity.shopping.Task;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.repository.ShoppingListRepository;
import com.shop.food.repository.TaskRepository;
import com.shop.food.service.iservice.FoodService;
import com.shop.food.service.iservice.GroupService;
import com.shop.food.service.iservice.ShoppingListTaskService;
import com.shop.food.service.iservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListTaskServiceImpl implements ShoppingListTaskService {

    private final ShoppingListRepository shoppingListRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final GroupService groupService;
    private final FoodService foodService;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ShoppingList createShoppingList(ShoppingListDto shoppingListDto) throws UserNotFoundException {


        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListDto.getName());
        shoppingList.setNote(shoppingListDto.getNote());

        try {
            Date parsedDate = dateFormatter.parse(shoppingListDto.getDate());
            shoppingList.setDate(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd. Ex: 2024-12-01");
        }

        shoppingList.setOwner(new User(shoppingListDto.getOwnerId()));

        if (shoppingListDto.getAssignToUserId() != null) {
            shoppingList.setAssignToUser(new User(shoppingListDto.getAssignToUserId()));
        }

        if (shoppingListDto.getBelongToGroupId() != null) {
            Group group = groupService.getGroup(shoppingListDto.getBelongToGroupId());
            shoppingList.setBelongToGroup(group);
        }

        return shoppingListRepository.save(shoppingList);
    }

    @Override
    public ShoppingList updateShoppingList(Integer listId, ShoppingListDto updateRequest) throws ResourceNotFoundException {
        ShoppingList shoppingList = shoppingListRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List not found"));

        shoppingList.setName(updateRequest.getName());
        shoppingList.setNote(updateRequest.getNote());

        try {
            Date parsedDate = dateFormatter.parse(updateRequest.getDate());
            shoppingList.setDate(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd. Ex: 2024-12-01");
        }

        if (updateRequest.getAssignToUserId() != null) {
            shoppingList.setAssignToUser(new User(updateRequest.getAssignToUserId()));
        }

        return shoppingListRepository.save(shoppingList);
    }

    @Override
    public void deleteShoppingList(Integer listId) {
        shoppingListRepository.deleteById(listId);
    }

    @Override
    public List<ShoppingList> getShoppingListByGroupId(Integer groupId) {
        return shoppingListRepository.getShoppingListsByGroupId(groupId);
    }

    @Override
    public List<ShoppingList> getShoppingListByUserId(Integer userId) {
        return shoppingListRepository.getShoppingListAssignedToUser(userId);
    }

    @Override
    public ShoppingList getShoppingList(Integer listId) throws ResourceNotFoundException {
        return shoppingListRepository.findById(listId).orElseThrow(() -> new ResourceNotFoundException("Shopping List not found"));
    }

    @Override
    public Task createTask(TaskDto taskDto,  Integer listId) throws ResourceNotFoundException {
        Task task = new Task();
        task.setQuantity(taskDto.getQuantity());
        task.setDone(taskDto.isDone());

        Food food = foodService.getFoodById(taskDto.getFoodId());
        task.setFood(food);

        ShoppingList shoppingList = shoppingListRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List not found"));
        task.setShoppingList(shoppingList);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> createTasks(List<TaskDto> taskDtos, Integer listId) throws ResourceNotFoundException {
        ShoppingList shoppingList = shoppingListRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping List not found"));
        List<Task> tasks = new ArrayList<>();
        for (TaskDto dto : taskDtos) {
            Task task = new Task();
            task.setQuantity(dto.getQuantity());
            task.setDone(dto.isDone());

            Food food = foodService.getFoodById(dto.getFoodId());
            task.setFood(food);
            task.setShoppingList(shoppingList);

            tasks.add(task);
        }
        return taskRepository.saveAll(tasks);
    }

    @Override
    public Task updateTask(Integer taskId, TaskDto taskDto) throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setQuantity(taskDto.getQuantity());
        task.setDone(taskDto.isDone());

        if (taskDto.getFoodId() != null) {
            Food food = foodService.getFoodById(taskDto.getFoodId());
            task.setFood(food);
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<ShoppingList> getReportOfUser(Integer userId, Integer day) {
        List<ShoppingList> shoppingLists = getShoppingListByUserId(userId);
        LocalDate today = LocalDate.now();

        return shoppingLists.stream()
                .filter(item -> {
                    LocalDate itemDate = item.getDate().toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(itemDate, today);
                    return Math.abs(daysBetween) <= day;
                })
                .collect(Collectors.toList());
    }
}