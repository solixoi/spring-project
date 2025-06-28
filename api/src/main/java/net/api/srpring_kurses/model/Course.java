package net.api.srpring_kurses.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(length = 50)
    private String subject;

    @Column(name = "class_number")
    private Long classNumber;

    public Long getClassNumber() {
        return classNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setClassNumber(Long classNumber) {
        this.classNumber = classNumber;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseSection> sections = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSections(List<CourseSection> sections) {
        this.sections = sections;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public List<CourseSection> getSections() {
        return sections;
    }

}
