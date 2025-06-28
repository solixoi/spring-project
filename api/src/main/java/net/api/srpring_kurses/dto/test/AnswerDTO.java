package net.api.srpring_kurses.dto.test;

import net.api.srpring_kurses.model.TestAnswer;

public class AnswerDTO {
    private String answerText;

    public AnswerDTO() {}

    public AnswerDTO(TestAnswer answer) {
        this.answerText = answer.getAnswerText();
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}
