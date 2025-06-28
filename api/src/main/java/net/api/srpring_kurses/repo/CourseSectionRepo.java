package net.api.srpring_kurses.repo;

import net.api.srpring_kurses.model.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface CourseSectionRepo extends JpaRepository<CourseSection, Long> {
    CourseSection findById(long id);

    @Query(value = "SELECT * FROM course_sections cs " +
            "WHERE cs.course_id = :courseId AND cs.id = :sectionId",
    nativeQuery = true)
    CourseSection findSectionCourses(@Param("courseId") Long courseId, @Param("sectionId") Long sectionId);

    @Query("SELECT c FROM CourseSection c " +
            "WHERE c.course.id = :id")
    CourseSection findCourseByIdDirect(Long id);

}
