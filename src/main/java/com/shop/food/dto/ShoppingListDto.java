package com.shop.food.dto;


import lombok.Data;

@Data
public class ShoppingListDto {
    private String name;
    private String note;
    private String date;
    private Integer assignToUserId;
    private Integer belongToGroupId;
    private Integer ownerId;
}
