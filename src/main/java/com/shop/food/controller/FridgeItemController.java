package com.shop.food.controller;

import com.shop.food.dto.FridgeItemDto;
import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.exception.ResourceNotFoundException;
import com.shop.food.service.iservice.FridgeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fridge")
@RequiredArgsConstructor
public class FridgeItemController {
    private final FridgeItemService fridgeItemService;

    @PostMapping
    public ResponseEntity<ResponseBody> createFridgeItem(@RequestBody FridgeItemDto fridgeItemDto) throws ResourceNotFoundException {
        FridgeItem createdItem = fridgeItemService.createFridgeItem(fridgeItemDto);
        return new ResponseEntity<>(new ResponseBody("Fridge item created successfully", "", createdItem), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateFridgeItem(@PathVariable Integer id, @RequestBody FridgeItemDto fridgeItemDto) throws ResourceNotFoundException {
        FridgeItem updatedItem = fridgeItemService.updateFridgeItem(id, fridgeItemDto);
        return new ResponseEntity<>(new ResponseBody("Fridge item updated successfully", "", updatedItem), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteFridgeItem(@PathVariable Integer id) throws ResourceNotFoundException {
        fridgeItemService.deleteFridgeItem(id);
        return new ResponseEntity<>(new ResponseBody("Fridge item deleted successfully", "", null), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> getFridgeItem(@PathVariable Integer id) throws ResourceNotFoundException {
        FridgeItem fridgeItem = fridgeItemService.getFridgeItemById(id);
        return new ResponseEntity<>(new ResponseBody("Fridge item fetched by id successfully", "", fridgeItem), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<ResponseBody> getFridgeItemsByGroupId(@PathVariable Integer groupId) {
        List<FridgeItem> items = fridgeItemService.getFridgeItemsByGroupId(groupId);
        return new ResponseEntity<>(new ResponseBody("Fridge items for group fetched successfully", "", items), HttpStatus.OK);
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<ResponseBody> getFridgeItemsByFoodId(@PathVariable Integer foodId) {
        List<FridgeItem> items = fridgeItemService.getFridgeItemsByFoodId(foodId);
        return new ResponseEntity<>(new ResponseBody("Fridge items by Food fetched successfully", "", items), HttpStatus.OK);
    }

}