package net.api.srpring_kurses.dto.user_profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.api.srpring_kurses.model.Course;
import net.api.srpring_kurses.model.StudentProgress;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserProfileCourseDTO {
    private Long id;
    private String title;
    @JsonProperty("image_path")
    private String imagePath;
    @JsonProperty("score_percent")
    private BigDecimal scorePercent;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;

    public UserProfileCourseDTO() {}

    public UserProfileCourseDTO(StudentProgress progress) {
        this.id = progress.getCourse().getId();
        this.title = progress.getCourse().getTitle();
        this.imagePath = progress.getCourse().getImagePath();
        this.scorePercent = progress.getScorePercent();
        this.startedAt = progress.getStartedAt();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getScorePercent() {
        return scorePercent;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setScorePercent(BigDecimal scorePercent) {
        this.scorePercent = scorePercent;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
