package com.shop.food.security.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String phoneNumber;
    private String fullName;
    private String password;
    private String language;
    private Integer timeZone;
    private String deviceId;
}
