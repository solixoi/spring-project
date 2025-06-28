package net.api.srpring_kurses.dto.test;

import net.api.srpring_kurses.model.Test;
import net.api.srpring_kurses.model.TestQuestion;

import java.util.ArrayList;
import java.util.List;

public class TestDTO {
    private Long id;
    private String title;

    private List<QuestionsDTO> answer = new ArrayList<>();

    public TestDTO(){}
    public TestDTO(Test test){
        this.id = test.getId();
        this.title = test.getTitle();
            for (TestQuestion testQuestion : test.getQuestions()) {
                answer.add(new QuestionsDTO(testQuestion));
            }
    }

    public Long getId() {
        return id;
    }

    public List<QuestionsDTO> getAnswer() {
        return answer;
    }

    public void setAnswer(List<QuestionsDTO> answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
