package com.shop.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RecipeDto {
    private String name;
    private String description;
    private String htmlContent;
    private Integer foodId;
    private List<Integer> rawMaterialIds = new ArrayList<>();
//
//    @JsonIgnore
//    private Integer authorId;
}
