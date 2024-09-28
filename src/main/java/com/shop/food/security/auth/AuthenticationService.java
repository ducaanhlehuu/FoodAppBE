package com.shop.food.security.auth;

import com.shop.food.model.user.Role;
import com.shop.food.model.user.User;
import com.shop.food.reposistory.UserReposistory;
import com.shop.food.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserReposistory reposistory;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder().email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .fullName(request.getFullName())
                        .password(encoder.encode(request.getPassword()))
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
