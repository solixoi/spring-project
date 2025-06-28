package net.api.srpring_kurses.service;

import net.api.srpring_kurses.model.Course;
import net.api.srpring_kurses.repo.CourseRepo;
import net.api.srpring_kurses.repo.CourseSectionRepo;
import net.api.srpring_kurses.repo.StudentSectionProgressRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepo courseRepo;
    private final StudentSectionProgressRepo studentSectionProgressRepo;

    public CourseService(CourseRepo courseRepo, StudentSectionProgressRepo studentSectionProgressRepo) {
        this.courseRepo = courseRepo;
        this.studentSectionProgressRepo = studentSectionProgressRepo;
    }

    public Course findById(long id) {
        return courseRepo.findById(id);
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public BigDecimal getSectionProgressByUserId(Long courseSectionId, Long userId) {
        return studentSectionProgressRepo.findHighestUserProgress(courseSectionId, userId);
    }

    public boolean saveCourse(Course course) {
//        course.setSections(course.getSections().stream()
//                .peek(section ->
//                        section.setCourse(course)
//                )
//                .collect(Collectors.toList())
//        );
//
//        course.getSections().forEach(section -> {
//            if (section.getTest() != null) {
//                section.getTest().setSection(section);
//            }
//        });
//        course.getSections().forEach(section -> {
//            if (section.getTest() != null) {
//                section.getTest().getQuestions().forEach(question -> {
//                    question.setTest(section.getTest());
//                });
//            }
//        });
        course.getSections().forEach(section -> {
            section.setCourse(course);
            if (section.getTest() != null) {
                section.getTest().setSection(section);
                section.getTest().getQuestions().forEach(question -> {
                    question.setTest(section.getTest());
                    question.getAnswers().forEach(answer -> {
                        answer.setQuestion(question);
                    });
                });
            }
        });
        Course newcCourse = courseRepo.save(course);
        return newcCourse == null ? false : true;
    }
}
