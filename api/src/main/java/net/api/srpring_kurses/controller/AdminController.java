package net.api.srpring_kurses.controller;


import net.api.srpring_kurses.dto.admin.UserDTO;
import net.api.srpring_kurses.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUser")
    public List<UserDTO> getAllUser() {
        return userService.getAllUsers();
    }
}
