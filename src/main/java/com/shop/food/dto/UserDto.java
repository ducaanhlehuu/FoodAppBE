package com.shop.food.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Integer timeZone;
    private String language;
    private String deviceId;
    private String photoUrl;
}
