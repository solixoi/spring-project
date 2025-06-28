package net.api.srpring_kurses.controller;

import net.api.srpring_kurses.model.Course;
import net.api.srpring_kurses.service.CourseService;
import net.api.srpring_kurses.service.JWTService;
import net.api.srpring_kurses.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final String RELATIVE_UPLOAD_DIR = "public/src/images/";

    private final UserService userService;
    private final JWTService jwtService;
    private final CourseService courseService;

    public TeacherController(UserService userService, JWTService jwtService, CourseService courseService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.courseService = courseService;
    }

    @PostMapping("/addCourses")
    public String addCourses(
            @RequestHeader("Authorization") String token,
            @RequestBody Course course
    ) {
        course.setTeacher(userService.findById(jwtService.decodeTokenGetId(token)));
        boolean save = courseService.saveCourse(course);
        return save?"success":"fail";
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String rootPath = System.getProperty("user.dir");
            Path uploadPath = Paths.get(rootPath).getParent().resolve("public/src/images");

            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> response = new HashMap<>();
            response.put("path", "src/images/" + fileName);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ошибка при сохранении файла"));
        }
    }

}
