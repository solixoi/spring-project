package net.api.srpring_kurses.repo;

import net.api.srpring_kurses.model.StudentSectionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StudentSectionProgressRepo extends JpaRepository<StudentSectionProgress, Long> {
    @Query("SELECT max(s.scorePercent) FROM StudentSectionProgress s " +
            "WHERE s.student.id = :userId AND s.section.id = :courseSectionId")
    BigDecimal findHighestUserProgress(Long courseSectionId, Long userId);

    @Query("SELECT min(s.endedAt) FROM StudentSectionProgress s " +
            "WHERE s.section.course.id = :courseId")
    LocalDateTime findStatedTimeByCourseId(Long courseId);

    @Query("SELECT count(distinct(s.section.id)) FROM StudentSectionProgress s " +
            "WHERE s.student.id = :userId AND s.isCompleted=true AND s.section.course.id = :courseId")
    Long findQuantityCompleteByCourseIdAndUserId(Long courseId, Long userId);
}
