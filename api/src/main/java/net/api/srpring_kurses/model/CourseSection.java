package net.api.srpring_kurses.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_sections")
public class CourseSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long sectionNumber;

    @Column(name = "theory_text", columnDefinition = "TEXT")
    private String theoryText;

    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(mappedBy = "section", cascade = CascadeType.ALL)
    private Test test;

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Long getSectionNumber() {
        return sectionNumber;
    }

    public String getTheoryText() {
        return theoryText;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Test getTest() {
        return test;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setSectionNumber(Long sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setTheoryText(String theoryText) {
        this.theoryText = theoryText;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

}
