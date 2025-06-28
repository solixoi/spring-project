package net.api.srpring_kurses.repo;

import net.api.srpring_kurses.model.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentProgressRepo extends JpaRepository<StudentProgress, Long> {
    @Query("SELECT s FROM StudentProgress s " +
            "WHERE s.course.id = :courseId AND s.student.id = :userId")
    StudentProgress findByUserIdAndCourseId(Long userId, Long courseId);
}
