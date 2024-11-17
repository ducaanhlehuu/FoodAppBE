package com.shop.food.dto;

import lombok.Data;

@Data
public class RecipeDto {
    private String name;
    private String description;
    private String htmlContent;
    private Integer foodId;
    private Integer authorId;
}
