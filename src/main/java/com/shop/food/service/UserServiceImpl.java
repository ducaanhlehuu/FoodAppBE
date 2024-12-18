package com.shop.food.service;

import com.shop.food.entity.user.User;
import com.shop.food.exception.PasswordNotMatchException;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.repository.UserRepository;
import com.shop.food.service.iservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userReposistory;
    private final PasswordEncoder encoder;
    @Override
    public List<User> getAllUser() {
        return userReposistory.findAll();
    }

    @Override
    public User getUser(Integer userId) {
        return userReposistory.findById(userId).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userReposistory.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        User existedUser = userReposistory.findById(user.getId()).orElse(null);
        if (existedUser==null) {
            return null;
        }
        user.setPassword(existedUser.getPassword());
        return user;
    }

    @Override
    public void changePassWord(Integer userId, String oldPassword, String newPassWord) throws UserNotFoundException, PasswordNotMatchException {
        User existedUser = userReposistory.findById(userId).orElse(null);
        if (existedUser==null) {
            throw new UserNotFoundException("User not found");
        }

        if (!encoder.matches(oldPassword, existedUser.getPassword())) {
            throw new PasswordNotMatchException("PassWord not match");
        }
        existedUser.setPassword(encoder.encode(newPassWord));
    }

    @Override
    public User saveNotificationToken(String email, String token) throws UserNotFoundException {
        User existedUser = userReposistory.findByEmail(email).orElse(null);
        if (existedUser==null) {
            throw new UserNotFoundException("User not found");
        }
        existedUser.setNotificationToken(token);
        return userReposistory.save(existedUser);
    }
}
