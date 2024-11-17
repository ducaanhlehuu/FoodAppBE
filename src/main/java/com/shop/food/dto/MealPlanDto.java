package com.shop.food.dto;


import lombok.Data;

@Data
public class MealPlanDto {
    private String name;
    private String timeStamp;
    private Integer foodId;
    private Integer ownerId;
    private Integer group;
}
