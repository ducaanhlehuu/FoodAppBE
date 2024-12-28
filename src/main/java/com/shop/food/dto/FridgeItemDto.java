package com.shop.food.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.Date;

@Data
public class FridgeItemDto {
    private String foodName;
    private Integer quantity;
    @Nullable
    private Integer useWithin;
    private String note;
    private Integer foodId;
}
