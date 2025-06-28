package net.api.srpring_kurses.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "test_answers")
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answerText;

    @JsonProperty("isCorrect")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private TestQuestion question;

    public Long getId() {
        return id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public TestQuestion getQuestion() {
        return question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public void setQuestion(TestQuestion question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
