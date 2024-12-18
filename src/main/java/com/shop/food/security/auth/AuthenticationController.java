package com.shop.food.security.auth;

import com.shop.food.entity.response.ResponseBody;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.external.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@RestController
@RequestMapping("api/user/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailService emailService;

    @Operation(summary = "Register", description = "Thêm mới tài khoản.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the user",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(summary = "User login", description = "This endpoint authenticates a user with provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws UnauthorizedException {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<ResponseBody> sendEmail(@RequestParam("email") String email, @Param("subject") String subject, @RequestParam("htmlContent") String htmlContent) {
        String verificationCode = UUID.randomUUID().toString().substring(0, 16);
        String body = !htmlContent.isEmpty() ? htmlContent
                : "<h3>Xin chào, mã xác nhận của bạn là: " + "<span style='color:blue;font-weight:bold;'>" + verificationCode + "</span></h3>";;
        try {
            emailService.sendEmail(email, subject, body);
        } catch (MailException e) {
            return ResponseEntity.ok(new ResponseBody("Send Email Failed","", ""));
        }
        return ResponseEntity.ok(new ResponseBody("Send Email success","", verificationCode));
    }

}
