package com.shop.food.util;

import com.shop.food.dto.NotificationMessage;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.service.external.EmailService;
import com.shop.food.service.external.FirebaseMessagingService;
import com.shop.food.service.iservice.GroupService;
import com.shop.food.service.iservice.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ServerUtil {


    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;


    public static String getAuthenticatedUserEmail() throws UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        throw new UnauthorizedException("Unauthorized");
    }

    @Transactional
    public User getAuthenticatedUser() throws UnauthorizedException, UserNotFoundException {
        String email = getAuthenticatedUserEmail();
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    public boolean checkUserInGroup(Integer groupId) throws UnauthorizedException {
        String email = ServerUtil.getAuthenticatedUserEmail();
        Group group = groupService.getGroup(groupId);
        if (group == null) {
            return false;
        }
        return  group.getMembers().stream().map(User::getEmail).toList().contains(email);
    }

    public User getCurrentUser() throws UnauthorizedException {
        String email = ServerUtil.getAuthenticatedUserEmail();
        return userService.getUserByEmail(email);
    }

//    public void sendEmail(String to, String title, String htmlContent) {
//        emailService.sendEmail(to,title,htmlContent);
//    }
//
//    public void sendNotification(NotificationMessage notificationMessage) throws Exception {
//        firebaseMessagingService.sendNotification(notificationMessage);
//    }
}
