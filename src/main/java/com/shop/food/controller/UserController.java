package com.shop.food.controller;

import com.shop.food.dto.ChangePasswordRequest;
import com.shop.food.dto.UserProfileUpdateRequest;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.entity.user.User;
import com.shop.food.exception.PasswordNotMatchException;
import com.shop.food.service.external.S3Service;
import com.shop.food.service.iservice.UserService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/user/edit")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;

    @PutMapping("/profile")
    public ResponseEntity<ResponseBody> editProfile(@RequestBody UserProfileUpdateRequest updateRequest) {
        try {
            String userEmail = ServerUtil.getAuthenticatedUserEmail();
            User user = userService.getUserByEmail(userEmail);

            user.setFullName(updateRequest.getFullName());
            user.setLanguage(updateRequest.getLanguage());
            user.setDeviceId(updateRequest.getDeviceId());
            user.setPhoneNumber(updateRequest.getPhoneNumber());

            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseBody("Profile updated successfully", "", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody("Error updating profile", "", ""));
        }
    }

    @PutMapping("/profile/photo")
    public ResponseEntity<ResponseBody> uploadProfilePhoto(@RequestParam("photo") MultipartFile photo) {
        try {
            if (photo == null || photo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseBody("No photo provided", "", ""));
            }
            if (!isValidImage(photo)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseBody("Invalid image format", "", ""));
            }

            String userEmail = ServerUtil.getAuthenticatedUserEmail();
            User user = userService.getUserByEmail(userEmail);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseBody("User not found", "", ""));
            }
            String photoUrl = s3Service.uploadFile(photo,  "avatar/" ,user.getFullName() + "_" + new Date().getTime());
            user.setPhotoUrl(photoUrl);
            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseBody("Profile photo uploaded successfully", "", photoUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody("Error uploading profile photo: " + e.getMessage(), "", ""));
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseBody> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            userService.changePassWord(changePasswordRequest.getUserId(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseBody("Password changed successfully", "", ""));
        } catch (PasswordNotMatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseBody(e.getMessage(), "", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody("Error changing password", "", ""));
        }
    }

    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
