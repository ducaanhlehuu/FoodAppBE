package com.shop.food.entity.meal;


import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.util.*;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date expiredDate;
    private String note;
    private String status = FridgeItem.STATUS_WAITING;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    @Immutable
    private Food food;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Immutable
    private User owner;

    @JsonIgnore public static String STATUS_EXPIRED = "EXPIRED";
    @JsonIgnore public static String STATUS_CANCELED = "CANCELED";
    @JsonIgnore public static String STATUS_DONE = "DONE";
    @JsonIgnore public static String STATUS_WAITING = "WAITING";

    @JsonProperty("group_id")
    public Integer getGroupId() {
        if (food!=null && food.getGroup()!=null) {
            return food.getGroup().getId();
        }
        return null;
    }

    public static List<String> getAllStatus(){
        return new ArrayList<>(Arrays.asList(STATUS_EXPIRED, STATUS_CANCELED, STATUS_DONE, STATUS_WAITING));
    }

    public String getStatus() {
        if (status == null) {
            status = FridgeItem.STATUS_WAITING;
        }
        return status;
    }
}
