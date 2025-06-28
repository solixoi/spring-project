package net.api.srpring_kurses.dto.course;


import net.api.srpring_kurses.model.CourseSection;

import java.math.BigDecimal;

public class CourseSectionsDTO {
    private Long id;
    private String title;
    private Long sectionNumber;
    private String theoryText;
    private String videoUrl;
    private boolean hasTest;
    private BigDecimal totalProgress;

    public CourseSectionsDTO(){}
    public CourseSectionsDTO(Long id, String title, Long sectionNumber, String theoryText, String videoUrl) {
        this.id = id;
        this.title = title;
        this.sectionNumber = sectionNumber;
        this.theoryText = theoryText;
        this.videoUrl = videoUrl;
    }
    public CourseSectionsDTO(CourseSection courseSection) {
        this.id = courseSection.getId();
        this.title = courseSection.getTitle();
        this.sectionNumber = courseSection.getSectionNumber();
        this.theoryText = courseSection.getTheoryText();
        this.videoUrl = courseSection.getVideoUrl();
        this.hasTest = false;
    }

    public String getTheoryText() {
        return theoryText;
    }

    public BigDecimal getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(BigDecimal totalProgress) {
        this.totalProgress = totalProgress;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(Long sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setTheoryText(String theoryText) {
        this.theoryText = theoryText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHasTest(boolean hasTest) {
        this.hasTest = hasTest;
    }

    public boolean isHasTest() {
        return hasTest;
    }
}
