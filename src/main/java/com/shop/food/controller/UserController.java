package com.shop.food.controller;

import com.shop.food.dto.ChangePasswordRequest;
import com.shop.food.dto.NotificationMessage;
import com.shop.food.dto.UserProfileUpdateRequest;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.entity.user.User;
import com.shop.food.exception.PasswordNotMatchException;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.service.external.FirebaseMessagingService;
import com.shop.food.service.external.S3Service;
import com.shop.food.service.iservice.UserService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shop.food.util.ServerUtil.getAuthenticatedUserEmail;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;
    private final FirebaseMessagingService firebaseMessagingService;

    @PutMapping("/edit/profile")
    public ResponseEntity<ResponseBody> editProfile(@RequestBody UserProfileUpdateRequest updateRequest) {
        try {
            String userEmail = getAuthenticatedUserEmail();
            User user = userService.getUserByEmail(userEmail);

            user.setFullName(updateRequest.getFullName());
            user.setLanguage(updateRequest.getLanguage());
            user.setDeviceId(updateRequest.getDeviceId());
            user.setPhoneNumber(updateRequest.getPhoneNumber());

            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseBody("Profile updated successfully", ResponseBody.SUCCESS, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody("Error updating profile", ResponseBody.SERVER_ERROR, ""));
        }
    }

    @PutMapping("/edit/profile/photo")
    public ResponseEntity<ResponseBody> uploadProfilePhoto(@RequestParam("photo") MultipartFile photo) {
        try {
            if (photo == null || photo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseBody("No photo provided", ResponseBody.BAD_REQUEST, ""));
            }
            if (!isValidImage(photo)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseBody("Invalid image format", ResponseBody.BAD_REQUEST, ""));
            }

            String userEmail = getAuthenticatedUserEmail();
            User user = userService.getUserByEmail(userEmail);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseBody("User not found", ResponseBody.NOT_FOUND, ""));
            }
            String photoUrl = s3Service.uploadFile(photo,  "avatar/" ,user.getFullName() + "_" + new Date().getTime());
            user.setPhotoUrl(photoUrl);
            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseBody("Profile photo uploaded successfully", ResponseBody.SUCCESS, photoUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody("Error uploading profile photo: " + e.getMessage(), ResponseBody.SERVER_ERROR, ""));
        }
    }

    @PutMapping("/edit/change-password")
    public ResponseEntity<ResponseBody> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            userService.changePassWord(changePasswordRequest.getUserId(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseBody("Password changed successfully", ResponseBody.SUCCESS, ""));
        } catch (PasswordNotMatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseBody(e.getMessage(), ResponseBody.BAD_REQUEST, ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody("Error changing password", ResponseBody.SERVER_ERROR, ""));
        }
    }

    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseBody> getUser() throws UnauthorizedException {
        String email =  ServerUtil.getAuthenticatedUserEmail();
        Map<String, Object> userInfo = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseBody("Get profile successfully", ResponseBody.SUCCESS, userService.getUserByEmail(email)));
    }

    @PostMapping("/send-notification")
    public ResponseEntity<ResponseBody> sendNotification(@RequestBody NotificationMessage notificationMessage) throws Exception {
        return ResponseEntity.ok(ResponseBody.builder().resultCode(ResponseBody.SUCCESS)
                .resultMessage(firebaseMessagingService.sendNotification(notificationMessage)).build());
    }

    @PostMapping("/save-notification-token")
    public ResponseEntity<ResponseBody> saveNotificationToken(@RequestParam String token) throws Exception {
        String email =  ServerUtil.getAuthenticatedUserEmail();
        return ResponseEntity.ok(ResponseBody.builder().resultCode(ResponseBody.SUCCESS).resultMessage("Lưu thành công Token: " + token)
                .data(userService.saveNotificationToken(email,token)).build());
    }

    @GetMapping("/search-user")
    public ResponseEntity<ResponseBody> searchUserByEmail(@RequestParam("email") String email) throws UserNotFoundException {
       User user =  userService.getUserByEmail(email);
       if (user == null) {
           throw new UserNotFoundException("Not found user email: " + email);
       }
       Map<Object, Object> data = new HashMap<>();
       data.put("id",user.getId());
       data.put("email",user.getEmail());
       data.put("fullName",user.getFullName());
       data.put("photoUrl",user.getPhotoUrl());
       return ResponseEntity.ok(ResponseBody.builder().resultCode(ResponseBody.SUCCESS).resultMessage("Found user").data(data).build());
    }
}
