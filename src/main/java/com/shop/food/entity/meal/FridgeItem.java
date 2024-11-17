package com.shop.food.entity.meal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.food.MeasureUnit;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.Date;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FridgeItem extends BaseEntity {
    private String foodName;
    private Integer quantity;
    private Date expiredDate;
    private String note;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    @Immutable
    private Food food;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    @Immutable
    private Group group;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Immutable
    private User owner;

    @JsonProperty("group_id")
    public Integer getGroupId() {
        return group != null ? group.getId() : null;
    }
}
