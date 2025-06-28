package net.api.srpring_kurses.requests_models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.api.srpring_kurses.filter.TimestampToLocalDateFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAnswerInformationREQ {
    private String timeSpent;

    @JsonDeserialize(using = TimestampToLocalDateFilter.class)
    private LocalDateTime endTime;

    private List<UserAnswerREQ> answers = new ArrayList<>();

    private Long courseId;

    private Long sectionId;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<UserAnswerREQ> getAnswers() {
        return answers;
    }


    public void setAnswers(List<UserAnswerREQ> answers) {
        this.answers = answers;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getTimeSpent() {
        return timeSpent;
    }


    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}
