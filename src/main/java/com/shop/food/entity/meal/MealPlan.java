package com.shop.food.entity.meal;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.food.entity.BaseEntity;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlan extends BaseEntity {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timeStamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS status; //

    @ManyToOne
    @JoinColumn(name = "food_id")
    @Immutable
    private Food food;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    @Immutable
    private User owner;

    @JsonProperty("owner_id")
    public Integer getOwnerId() {
        return owner != null ? owner.getId() : null;
    }
    
    @JsonProperty("group_id")
    public Integer getGroupId() {
        if (food!=null && food.getGroup()!=null) {
            return food.getGroup().getId();
        }
        return null;
    }

//    public Map<Integer, String> getStatusNameMap() {
//        return new Hash
//    };
}
