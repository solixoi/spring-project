package net.api.srpring_kurses.dto.course;


import net.api.srpring_kurses.model.Course;

import java.math.BigDecimal;

public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private String subject;
    private Long classNumber;
    private BigDecimal scorePercent;

    public CourseDTO() {}

    public CourseDTO(Long id, String title, String description, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public BigDecimal getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(BigDecimal scorePercent) {
        this.scorePercent = scorePercent;
    }

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.imagePath = course.getImagePath();
        this.subject = course.getSubject();
        this.classNumber = course.getClassNumber();
    }

    public String getSubject() {
        return subject;
    }

    public Long getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Long classNumber) {
        this.classNumber = classNumber;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Title: " + title + ", Description: " + description + ", ImagePath: " + imagePath;
    }
}
