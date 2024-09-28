package com.shop.food.entity.shopping;

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
    private Food food;
    private Integer quantity;
    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;
}
