package com.shop.food.entity.food;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Food extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 50)
    private String type;
    @Column(length = 255)
    private String description;
    @Column(length = 255)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "measure_unit_id")
    private MeasureUnit measureUnit;

    @ManyToOne
    @JoinColumn(name = "food_category_id")
    private FoodCategory foodCategory;

    @ManyToOne
    @JoinColumn(name="group_id")
    @JsonIgnore
    private Group group;

    @JsonProperty("groupId")
    public Integer getGroupId(){
        return group!=null? group.getId() : null;
    }
}
