package com.shop.food.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DtoBasic {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
