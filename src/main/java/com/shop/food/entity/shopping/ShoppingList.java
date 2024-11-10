package com.shop.food.entity.shopping;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingList extends BaseEntity {
    private String name;
    private String note;
    private String date;

    @ManyToOne
    @JoinColumn(name="assign_to_user_id")
    @JsonIgnore
    private User assignToUser;

    @ManyToOne
    @JoinColumn(name="belong_to_group_id")
    @JsonIgnore
    private Group belongToGroup;

    @ManyToOne
    @JoinColumn(name="owner_id")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> details = new ArrayList<>();

    @JsonProperty("belong_to_group_id")
    public Integer getGroupId() {
        return belongToGroup != null ? belongToGroup.getId() : null;
    }
    @JsonProperty("assign_to_user_id")
    public Integer getAssignToUserId() {
        return assignToUser != null ? assignToUser.getId() : null;
    }
    @JsonProperty("owner_id")
    public Integer getOwnerId() {
        return owner != null ? owner.getId() : null;
    }
}
