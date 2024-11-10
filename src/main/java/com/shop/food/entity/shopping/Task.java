package com.shop.food.entity.shopping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.food.Food;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Food food;

    private Integer quantity;

    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    @JsonIgnore
    private ShoppingList shoppingList;
    @JsonProperty("food_id")
    public Integer getFoodId() {
        return food != null ? food.getId() : null;
    }
    @JsonProperty("shopping_list_id")
    public Integer getShoppingListId() {
        return shoppingList != null ? shoppingList.getId() : null;
    }
}
