package com.shop.food.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfileUpdateRequest {
    private String fullName;
    private String language;
    private String deviceId;
    private String phoneNumber;
}
