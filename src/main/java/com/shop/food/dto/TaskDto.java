package com.shop.food.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Integer foodId;
    private Integer quantity;
    private boolean done;
}
