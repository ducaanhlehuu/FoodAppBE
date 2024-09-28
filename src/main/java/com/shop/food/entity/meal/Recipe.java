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
public class Recipe extends BaseEntity {
    @Column(length = 128, nullable = false)
    private String name;
    @Column(length = 256, nullable = false)
    private String description;
    @Column(length = 2056)
    private String htmlContent;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

}
