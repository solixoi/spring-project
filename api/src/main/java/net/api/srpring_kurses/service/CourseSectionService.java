package net.api.srpring_kurses.service;

import net.api.srpring_kurses.model.CourseSection;
import net.api.srpring_kurses.repo.CourseSectionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSectionService {
    private final CourseSectionRepo courseSectionRepo;

    public CourseSectionService(CourseSectionRepo courseSectionRepo) {
        this.courseSectionRepo = courseSectionRepo;
    }

    public CourseSection findById(long id) {
        return courseSectionRepo.findById(id);
    }

    public CourseSection findSectionCourses(Long courseId, Long sectionId) {
        return courseSectionRepo.findSectionCourses(courseId, sectionId);
    }

}

