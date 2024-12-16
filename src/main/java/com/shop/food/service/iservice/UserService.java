package com.shop.food.service.iservice;

import com.shop.food.entity.user.User;
import com.shop.food.exception.PasswordNotMatchException;
import com.shop.food.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();
    public User getUser(Integer userId);
    public User getUserByEmail(String email);
    public User saveUser(User User);
    public void changePassWord(Integer userId, String oldPassword, String newPassWord) throws UserNotFoundException, PasswordNotMatchException;
    public User saveNotificationToken(String email, String token) throws UserNotFoundException;
}
