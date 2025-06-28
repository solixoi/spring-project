package net.api.srpring_kurses.controller;


import net.api.srpring_kurses.dto.course.CourseDTO;
import net.api.srpring_kurses.dto.course.CourseSectionsDTO;
import net.api.srpring_kurses.model.Course;
import net.api.srpring_kurses.model.CourseSection;
import net.api.srpring_kurses.model.StudentProgress;
import net.api.srpring_kurses.service.CourseSectionService;
import net.api.srpring_kurses.service.CourseService;
import net.api.srpring_kurses.service.JWTService;
import net.api.srpring_kurses.service.StudentProgressService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {
    private final CourseService courseService;
    private final JWTService jwtService;
    private final StudentProgressService studentProgressService;

    public CoursesController(CourseService courseService, CourseSectionService courseSectionService, JWTService jwtService, StudentProgressService studentProgressService) {
        this.courseService = courseService;
        this.jwtService = jwtService;
        this.studentProgressService = studentProgressService;
    }

    @GetMapping("about/course/{id}")
    public List<CourseSectionsDTO> getCourseById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        Course course = courseService.findById(id);
        if (course.getSections() == null) {
            return null;
        }
        List<CourseSectionsDTO> courseSectionsDTOList = new ArrayList<>();
        for (CourseSection courseSection : course.getSections()) {
            CourseSectionsDTO courseSectionsDTO = new CourseSectionsDTO(courseSection);
            courseSectionsDTO.setHasTest(courseSection.getTest() != null);
            courseSectionsDTO.setTotalProgress(courseService.getSectionProgressByUserId(
                    courseSection.getId() ,jwtService.decodeTokenGetId(authorizationHeader)));
            courseSectionsDTOList.add(courseSectionsDTO);
        }
        return courseSectionsDTOList;
    }

    @GetMapping("/getAll")
    public List<CourseDTO> getAllCourses(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        List<Course> courses = courseService.findAll();
        if (courses == null) {
            return null;
        }
        Long userId = jwtService.decodeTokenGetId(authorizationHeader);
        return courses.stream()
                .map(course -> {
                    CourseDTO dto = new CourseDTO(course);
                    StudentProgress progress = studentProgressService.findByUserIdAndCourseId(userId, course.getId());
                    dto.setScorePercent(progress != null ? progress.getScorePercent() : null);
                    return dto;
                })
                .collect(Collectors.toList());

    }
}
