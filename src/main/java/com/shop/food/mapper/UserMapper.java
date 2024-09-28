package com.shop.food.mapper;

import com.shop.food.dto.UserDto;
import com.shop.food.entity.user.Role;
import com.shop.food.entity.user.User;

public class UserMapper {
    public static User getUserFromUserDto(UserDto userDto) {
        return  User.builder()
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .role(Role.USER)
                .deviceId(userDto.getDeviceId())
                .language(userDto.getLanguage())
                .fullName(userDto.getFullName())
                .photoUrl(userDto.getPhotoUrl())
                .timeZone(userDto.getTimeZone())
                .build();
    }

    public static UserDto getUserDtoFromUser(User user) {
        return  UserDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .deviceId(user.getDeviceId())
                .language(user.getLanguage())
                .fullName(user.getFullName())
                .photoUrl(user.getPhotoUrl())
                .timeZone(user.getTimeZone())
                .build();
    }
}
