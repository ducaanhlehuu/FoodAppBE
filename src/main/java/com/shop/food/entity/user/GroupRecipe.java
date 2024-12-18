package com.shop.food.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.food.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_recipe", uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "recipe_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupRecipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "group_id")
    @JsonIgnore
    private Integer groupId;

    @Column(name = "recipe_id")
    private Integer recipeId;

    private LocalDateTime timeStamp;

    @PrePersist
    protected void onCreate() {
        this.timeStamp = LocalDateTime.now();
    }


}
