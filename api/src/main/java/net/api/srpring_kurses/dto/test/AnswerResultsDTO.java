package net.api.srpring_kurses.dto.test;


import java.util.ArrayList;
import java.util.List;

public class AnswerResultsDTO {
    private Long quantityCorrectAnswer;
    private Long totalQuestions;
    private List<Long> incorrectAnswerId = new ArrayList<Long>();


    public List<Long> getIncorrectAnswerId() {
        return incorrectAnswerId;
    }

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public Long getQuantityCorrectAnswer() {
        return quantityCorrectAnswer;
    }

    public void setIncorrectAnswerId(List<Long> incorrectAnswerId) {
        this.incorrectAnswerId = incorrectAnswerId;
    }

    public void setQuantityCorrectAnswer(Long quantityCorrectAnswer) {
        this.quantityCorrectAnswer = quantityCorrectAnswer;
    }

    public void setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
