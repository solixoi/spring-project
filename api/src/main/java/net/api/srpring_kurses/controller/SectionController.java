package net.api.srpring_kurses.controller;


import net.api.srpring_kurses.dto.course.CourseSectionsDTO;
import net.api.srpring_kurses.model.CourseSection;
import net.api.srpring_kurses.service.CourseSectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    private final CourseSectionService courseSectionService;
    public SectionController(CourseSectionService courseSectionService) {
        this.courseSectionService = courseSectionService;
    }

    @GetMapping("/get/information")
    public CourseSectionsDTO getCourseSections(
            @RequestParam Long courseId,
            @RequestParam Long sectionId
    ) {
        CourseSection courseSection = courseSectionService.findSectionCourses(courseId, sectionId);
        if (courseSection != null) {
            CourseSectionsDTO courseSectionsDTO = new CourseSectionsDTO(courseSection);
            courseSectionsDTO.setHasTest(courseSection.getTest() != null);
            return courseSectionsDTO;
        }
        return null;
    }
}
