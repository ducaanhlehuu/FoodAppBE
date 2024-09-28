package com.shop.food.entity.food;


import com.shop.food.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCategory extends BaseEntity {
    private String name;
    private String alias;
    private String description;
}
