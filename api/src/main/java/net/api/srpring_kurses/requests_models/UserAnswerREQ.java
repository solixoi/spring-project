package net.api.srpring_kurses.requests_models;

public class UserAnswerREQ {
    private Long id;
    private String currentAnswer;

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setCurrentAnswer(String currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
