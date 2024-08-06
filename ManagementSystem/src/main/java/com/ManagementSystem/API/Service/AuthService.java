package com.ManagementSystem.API.Service;

import com.ManagementSystem.API.Entities.AuthenticationResponse;
import com.ManagementSystem.API.Entities.User;
import com.ManagementSystem.API.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public User register(User request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        user.setProfileHeadline(request.getProfileHeadline());
        user.setRole(request.getRole());
        user.setProfile(request.getProfile());

        user = userRepository.save(user);

        return user;
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPasswordHash()
                )
        );
        User user = userRepository.findByUserName(request.getUserName()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
