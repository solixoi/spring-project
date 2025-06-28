package net.api.srpring_kurses.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_questions")
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "answer_option")
    private String answerOption;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TestAnswer> answers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public Test getTest() {
        return test;
    }

    public List<TestAnswer> getAnswers() {
        return answers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setAnswers(List<TestAnswer> answers) {
        this.answers = answers;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
