package com.shop.food.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class GroupDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean enable;
}
