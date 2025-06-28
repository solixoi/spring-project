package net.api.srpring_kurses.dto.user_profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.api.srpring_kurses.model.Course;
import net.api.srpring_kurses.model.StudentSectionProgress;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserProfileSectionDTO {
    private Long id;
    @JsonProperty("course_id")
    private Long courseId;
    @JsonProperty("section_number")
    private Long sectionNumber;
    @JsonProperty("score_percent")
    private BigDecimal scorePercent;
    private Long attempts;
    @JsonProperty("ended_at")
    private LocalDateTime endedAt;
    @JsonProperty("completed_at")
    private String completedAt;

    public UserProfileSectionDTO() {}

    public UserProfileSectionDTO(StudentSectionProgress progress) {
        this.id = progress.getId();
        this.courseId = progress.getSection().getCourse().getId();
        this.sectionNumber = progress.getSection().getSectionNumber();
        this.scorePercent = progress.getScorePercent();
        this.endedAt = progress.getEndedAt();
        this.completedAt = progress.getCompletedAt();
    }

    public BigDecimal getScorePercent() {
        return scorePercent;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public Long getAttempts() {
        return attempts;
    }

    public Long getSectionNumber() {
        return sectionNumber;
    }

    public void setScorePercent(BigDecimal scorePercent) {
        this.scorePercent = scorePercent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setSectionNumber(Long sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public void setAttempts(Long attempts) {
        this.attempts = attempts;
    }
}
