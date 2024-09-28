package com.shop.food.model;

import com.shop.food.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class FamilyGroup extends BaseEntity{
    private String name;
    private String description;
    private Long ownerId = -1L;
    @ManyToMany
    @JoinTable(
            name = "family_group_members",
            joinColumns = @JoinColumn(name = "family_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> familyMembers;
}
