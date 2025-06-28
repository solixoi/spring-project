package net.api.srpring_kurses.repo;

import net.api.srpring_kurses.model.CourseSection;
import net.api.srpring_kurses.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepo extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t " +
            "WHERE t.section.id = :sectionId and t.section.course.id = :courseId")
    Test findByCourseIdAndSectionId(@Param("courseId") Long courseId, @Param("sectionId") Long sectionId);

    @Query("SELECT t FROM Test t " +
            "WHERE t.section.id = :sectionId AND t.section.course.id = :courseId")
    Test findBySectionIdAndCourseId(Long courseId, Long sectionId);

    @Query("SELECT count(t.id) FROM Test t " +
            "WHERE t.section.course.id = :courseId")
    Long findQuantityTestByCourseId(Long courseId);

}
