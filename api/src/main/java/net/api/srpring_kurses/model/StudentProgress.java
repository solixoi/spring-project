package net.api.srpring_kurses.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_progress")
public class StudentProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startedAt;

    private BigDecimal scorePercent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public BigDecimal getScorePercent() {
        return scorePercent;
    }

    public User getStudent() {
        return student;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setScorePercent(BigDecimal scorePercent) {
        this.scorePercent = scorePercent;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
}
