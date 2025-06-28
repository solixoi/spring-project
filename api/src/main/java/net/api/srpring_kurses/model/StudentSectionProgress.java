package net.api.srpring_kurses.model;

import jakarta.persistence.*;
import net.api.srpring_kurses.requests_models.UserAnswerInformationREQ;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_section_progress")
public class StudentSectionProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isCompleted = false;

    private String completedAt;

    private LocalDateTime endedAt;

    @Column(precision = 5, scale = 2)
    private BigDecimal scorePercent;

    public StudentSectionProgress() {}

    public StudentSectionProgress(UserAnswerInformationREQ answerReq) {
        this.completedAt = answerReq.getTimeSpent();
        this.endedAt = answerReq.getEndTime();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private CourseSection section;


    public CourseSection getSection() {
        return section;
    }

    public Long getId() {
        return id;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public User getStudent() {
        return student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSection(CourseSection section) {
        this.section = section;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public BigDecimal getScorePercent() {
        return scorePercent;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setScorePercent(BigDecimal scorePercent) {
        this.scorePercent = scorePercent;
    }
}