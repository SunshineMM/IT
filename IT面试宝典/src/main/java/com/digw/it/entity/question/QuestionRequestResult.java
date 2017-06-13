package com.digw.it.entity.question;

import java.io.Serializable;
import java.util.List;

/**
 * digw创建于17-6-13.
 */

public class QuestionRequestResult implements Serializable{
    private Paper paper;
    private List<QuestionInfo> allQuestion;
    private List<UserAnswer> userAnswers;

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public List<QuestionInfo> getAllQuestion() {
        return allQuestion;
    }

    public void setAllQuestion(List<QuestionInfo> allQuestion) {
        this.allQuestion = allQuestion;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
