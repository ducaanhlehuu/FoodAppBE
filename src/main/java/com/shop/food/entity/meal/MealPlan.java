package com.shop.food.entity.meal;


import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.user.User;
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
public class MealPlan extends BaseEntity {

    private String name;
    private String timeStamp;
    private String status;

    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User owner;

//    public Map<Integer, String> getStatusNameMap() {
//        return new Hash
//    };
}
