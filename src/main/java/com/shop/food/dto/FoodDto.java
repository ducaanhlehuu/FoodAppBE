package com.shop.food.dto;

import com.shop.food.entity.food.DtoBasic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = false)
@Data
public class FoodDto  {
    private String name;
    private String type;
    private String description;
    private MultipartFile image;
    private Integer groupId;
    private Integer ownerId;
    private String unitName;
    private String foodCategoryAlias;
}
