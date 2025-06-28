package net.api.srpring_kurses.service;

import net.api.srpring_kurses.dto.user_profile.UserProfileSectionDTO;
import net.api.srpring_kurses.model.StudentProgress;
import net.api.srpring_kurses.model.StudentSectionProgress;
import net.api.srpring_kurses.model.User;
import net.api.srpring_kurses.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentProgressService {

    private final UserRepo userRepo;
    private final CourseRepo courseRepo;
    private final StudentProgressRepo studentProgressRepo;
    private final StudentSectionProgressRepo studentSectionProgressRepo;
    private final TestRepo testRepo;

    public StudentProgressService(UserRepo userRepo, CourseRepo courseRepo, StudentProgressRepo studentProgressRepo, StudentSectionProgressRepo studentSectionProgressRepo, TestRepo testRepo) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.studentProgressRepo = studentProgressRepo;
        this.studentSectionProgressRepo = studentSectionProgressRepo;
        this.testRepo = testRepo;
    }

    public StudentProgress findByUserIdAndCourseId(Long userId, Long courseId) {
        return studentProgressRepo.findByUserIdAndCourseId(userId, courseId);
    }

    @Transactional
    public void updateStudentCourseProgress(Long courseId, Long userId) {
        Long quantityTestInCourses;
        Long quantitySectionUserComplete;
        BigDecimal scorePercent = BigDecimal.ZERO;
        StudentProgress studentProgress = studentProgressRepo.findByUserIdAndCourseId(userId, courseId);
        if (studentProgress != null) {
            if (studentProgress.getScorePercent().compareTo(BigDecimal.valueOf(100)) == 0) {
                return;
            }
            quantityTestInCourses = testRepo.findQuantityTestByCourseId(courseId);
            quantitySectionUserComplete = studentSectionProgressRepo.findQuantityCompleteByCourseIdAndUserId(courseId, userId);
            if (quantityTestInCourses != null && quantityTestInCourses != 0) {
                scorePercent = BigDecimal.valueOf(quantitySectionUserComplete)
                        .divide(BigDecimal.valueOf(quantityTestInCourses), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP);
            }
            studentProgress.setScorePercent(
                    studentProgress.getScorePercent().compareTo(scorePercent) >= 0
                            ? studentProgress.getScorePercent()
                            : scorePercent
            );

            studentProgressRepo.save(studentProgress);
            return;
        }
        StudentProgress newStudentProgress = new StudentProgress();
        newStudentProgress.setCourse(courseRepo.findById((long )courseId));
        newStudentProgress.setStudent(userRepo.findByIdDirect(userId));
        newStudentProgress.setStartedAt(studentSectionProgressRepo.findStatedTimeByCourseId(courseId));
        quantityTestInCourses = testRepo.findQuantityTestByCourseId(courseId);
        quantitySectionUserComplete = studentSectionProgressRepo.findQuantityCompleteByCourseIdAndUserId(courseId, userId);
        scorePercent = BigDecimal.ZERO;
        if (quantityTestInCourses != null && quantityTestInCourses != 0) {
            scorePercent = BigDecimal.valueOf(quantitySectionUserComplete)
                    .divide(BigDecimal.valueOf(quantityTestInCourses), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }
        newStudentProgress.setScorePercent(scorePercent);
        studentProgressRepo.save(newStudentProgress);
    }

    public List<UserProfileSectionDTO> getUserProfileSectionsByUser(List<StudentSectionProgress> studentSectionProgress) {
        Map<Long, List<StudentSectionProgress>> grouped = Optional.ofNullable(studentSectionProgress)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.groupingBy(s -> s.getSection().getId()));

        return grouped.values().stream()
                .map(progressList -> {
                    StudentSectionProgress maxProgress = progressList.stream()
                            .max(Comparator
                                    .comparing(StudentSectionProgress::getScorePercent)
                                    .thenComparing(StudentSectionProgress::getId, Comparator.reverseOrder())
                            )
                            .orElse(null);

                    long totalAttempts = progressList.size();
                    UserProfileSectionDTO dto = new UserProfileSectionDTO(maxProgress);
                    dto.setAttempts(totalAttempts);
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
