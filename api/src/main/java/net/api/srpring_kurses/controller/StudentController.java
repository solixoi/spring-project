package net.api.srpring_kurses.controller;

import net.api.srpring_kurses.dto.user_profile.UserProfileDTO;
import net.api.srpring_kurses.model.User;
import net.api.srpring_kurses.service.JWTService;
import net.api.srpring_kurses.service.StudentProgressService;
import net.api.srpring_kurses.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class StudentController {
    private final UserService userService;
    private final JWTService jwtService;
    private final StudentProgressService studentProgressService;

    public StudentController(UserService userService, JWTService jwtService, StudentProgressService studentProgressService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.studentProgressService = studentProgressService;
    }

    @GetMapping("/profile")
    public UserProfileDTO getProfileData(
            @RequestHeader("Authorization") String authorizationHeader
     ) {
        User user = userService.findById(jwtService.decodeTokenGetId(authorizationHeader));
        UserProfileDTO userProfileDTO = new UserProfileDTO(user);
        userProfileDTO.setSections(studentProgressService.getUserProfileSectionsByUser(user.getSectionProgressList()));
        return userProfileDTO;
    }
}
