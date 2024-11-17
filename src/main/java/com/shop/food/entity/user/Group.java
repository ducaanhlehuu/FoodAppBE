package com.shop.food.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.dto.GroupDto;
import com.shop.food.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
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
    @JsonIgnore
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
    @JsonProperty("owner_id")
    public Integer getOwnerId() {
        return owner != null ? owner.getId() : null;
    }
}
