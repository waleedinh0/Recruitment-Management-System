package com.ManagementSystem.API.Controller;

import com.ManagementSystem.API.Entities.User;
import com.ManagementSystem.API.Service.AuthService;
import com.ManagementSystem.API.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        if(user == null || user.getUserName() == null || user.getEmail() == null ||  user.getAddress() == null || user.getRole() == null || user.getPasswordHash() == null || user.getProfileHeadline() == null ){
            return ResponseEntity.badRequest().body("Invalid Input");
        }
        User savedUser = authService.register(user);
        if(savedUser != null){
            return ResponseEntity.ok().body("Signup Successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Signup");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        Optional<User> foundUser = userService.findByUserName(user.getUserName());
        if (foundUser.isEmpty() || !passwordEncoder.matches(user.getPasswordHash(), foundUser.get().getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(authService.authenticate(user));
    }
}
