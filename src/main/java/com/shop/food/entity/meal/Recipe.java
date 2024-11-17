package com.shop.food.entity.meal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

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
    @Immutable
    private Food food;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @Immutable
    private User author;
}
