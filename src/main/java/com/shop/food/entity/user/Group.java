package com.shop.food.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shop.food.dto.GroupDto;
import com.shop.food.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_group")
@Data
public class Group extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 255)
    private String description;
    private boolean enable = true;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_member",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<User> members;

    public void updateGroup(Group group) {
        this.name = group.getName();
        this.description = group.getDescription();
        this.enable = group.isEnable();
    }
}
