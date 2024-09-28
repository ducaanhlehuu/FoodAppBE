package com.shop.food.controller;

import com.shop.food.dto.FoodDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.food.FoodCategory;
import com.shop.food.entity.food.MeasureUnit;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.iservice.FoodService;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.service.iservice.GroupService;
import com.shop.food.service.iservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final GroupService groupService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createFood(@RequestBody FoodDto foodDto) throws UnauthorizedException {
        String email = getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);
        if(user == null) {
            return new ResponseEntity<>(new ResponseBody("User not found", "Fail", null), HttpStatus.BAD_REQUEST);
        }
        foodDto.setOwnerId(user.getId());
        Food createdFood = foodService.createFood(foodDto);
        return new ResponseEntity<>(new ResponseBody("Food created successfully", "Success", createdFood), HttpStatus.CREATED);
    }

    @PutMapping("/update/{foodId}")
    public ResponseEntity<ResponseBody> updateFood(@RequestBody FoodDto foodDto, @PathVariable("foodId") Integer foodId) {
        Food updatedFood = foodService.updateFood(foodDto, foodId);
        return new ResponseEntity<>(new ResponseBody("Food updated successfully", "Success", updatedFood), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<ResponseBody> deleteFood(@PathVariable Integer foodId) {
        foodService.deleteFood(foodId);
        return new ResponseEntity<>(new ResponseBody("Food deleted successfully", "Success", null), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<ResponseBody> getAllFoods(@PathVariable Integer groupId) throws UnauthorizedException {
        if (!checkUserInGroup(groupId) ) {
            throw new UnauthorizedException("Not in this group");
        }
        List<Food> foods = foodService.getAllFoodsInGroup(groupId);
        return new ResponseEntity<>(new ResponseBody("Retrieved all foods in group", "Success", foods), HttpStatus.OK);
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<ResponseBody> getFoodById(@PathVariable Integer foodId) {
        Food food = foodService.getFoodById(foodId);
        if (food == null) {
            return new ResponseEntity<>(new ResponseBody("Food not found", "Error", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseBody("Retrieved food", "Success", food), HttpStatus.OK);
    }

    private String getAuthenticatedUserEmail() throws UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        throw new UnauthorizedException("Unauthorized");
    }



    private boolean checkUserInGroup(Integer groupId) throws UnauthorizedException {
        String email = getAuthenticatedUserEmail();
        Group group = groupService.getGroup(groupId);
        if (group == null) {
            return false;
        }
        return  group.getMembers().stream().map(User::getEmail).toList().contains(email);
    }
}
