package net.api.srpring_kurses.dto.test;

import net.api.srpring_kurses.model.TestAnswer;
import net.api.srpring_kurses.model.TestQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuestionsDTO {
    private Long id;
    private String questionText;
    private String answerOption;
    List<AnswerDTO> answers = new ArrayList<AnswerDTO>();

    public QuestionsDTO(){}

    public QuestionsDTO(TestQuestion test){
        this.id = test.getId();
        this.questionText = test.getQuestionText();
        this.answerOption = test.getAnswerOption();
        if (test.getAnswers() != null) {
            for (TestAnswer testAnswer : test.getAnswers()) {
                answers.add(new AnswerDTO(testAnswer));
            }
        } else {
            answers = null;
        }
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
