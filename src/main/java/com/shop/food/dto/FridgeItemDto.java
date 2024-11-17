package com.shop.food.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FridgeItemDto {
    private String foodName;
    private Integer quantity;
    private Integer useWithin;
    private String note;
    private Integer foodId;
    private Integer groupId;
    private Integer ownerId;
}
