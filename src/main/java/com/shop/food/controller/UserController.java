package com.shop.food.controller;

import com.shop.food.dto.ChangePasswordRequest;
import com.shop.food.dto.UserProfileUpdateRequest;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.entity.user.User;
import com.shop.food.exception.PasswordNotMatchException;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.external.S3Service;
import com.shop.food.service.iservice.UserService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.shop.food.util.ServerUtil.getAuthenticatedUserEmail;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;

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

    @GetMapping("/extract/profile")
    public ResponseEntity<ResponseBody> getUser(@PathVariable("userId") Integer userId) throws UnauthorizedException {
        String email =  ServerUtil.getAuthenticatedUserEmail();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseBody("Get profile successfully", ResponseBody.SUCCESS, userService.getUserByEmail(email)));
    }

}
