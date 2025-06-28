package net.api.srpring_kurses.controller;

import net.api.srpring_kurses.model.User;
import net.api.srpring_kurses.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user) != null ? "Success register!" : "Username or email already exists!" ;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken() {
        return ResponseEntity.ok().build();
    }
}
