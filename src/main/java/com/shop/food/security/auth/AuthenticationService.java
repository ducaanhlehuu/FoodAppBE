package com.shop.food.security.auth;

import com.shop.food.entity.user.Role;
import com.shop.food.entity.user.User;
import com.shop.food.repository.UserRepository;
import com.shop.food.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository reposistory;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder().email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .fullName(request.getFullName())
                        .password(encoder.encode(request.getPassword()))
                        .deviceId(request.getDeviceId())
                        .timeZone(request.getTimeZone())
                        .language(request.getLanguage())
                        .role(Role.USER).build();
        reposistory.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = reposistory.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}
