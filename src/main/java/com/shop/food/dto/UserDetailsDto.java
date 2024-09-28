package com.shop.food.dto;

import com.shop.food.entity.user.Group;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Data
public class UserDetailsDto {
    private String email;
    private String fullName;
    private String phoneNumber;
    private Integer timeZone;
    private String language;
    private String deviceId;
    private String photoUrl;
    private String role;

    private List<GroupDto> groups;
}
