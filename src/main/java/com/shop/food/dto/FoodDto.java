package com.shop.food.dto;

import com.shop.food.entity.food.DtoBasic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class FoodDto  {
    private String name;
    private String type;
    private String description;
    private String imageUrl;
    private Integer ownerId;
    private Integer groupId;
    private String unitName;
    private String foodCategoryAlias;
}
