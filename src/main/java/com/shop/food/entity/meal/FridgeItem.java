package com.shop.food.entity.meal;


import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.food.MeasureUnit;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FridgeItem extends BaseEntity {
    private String foodName;
    private String useWithin;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name= "measure_unit_id")
    private MeasureUnit measureUnit;
}
