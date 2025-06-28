package net.api.srpring_kurses.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToOne
    @JoinColumn(name = "section_id")
    private CourseSection section;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestQuestion> questions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public CourseSection getSection() {
        return section;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSection(CourseSection section) {
        this.section = section;
    }

    public void setQuestions(List<TestQuestion> questions) {
        this.questions = questions;
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

}
