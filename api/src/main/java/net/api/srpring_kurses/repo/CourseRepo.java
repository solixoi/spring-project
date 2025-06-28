package net.api.srpring_kurses.repo;


import net.api.srpring_kurses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Course findById(long id);
}
