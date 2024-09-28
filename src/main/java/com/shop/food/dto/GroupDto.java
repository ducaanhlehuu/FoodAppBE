package com.shop.food.dto;

import com.shop.food.entity.food.DtoBasic;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class GroupDto extends DtoBasic {
    private Integer id;
    private String groupName;
    private String description;
    private boolean enable;
}
