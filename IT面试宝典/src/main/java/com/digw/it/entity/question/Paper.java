package com.digw.it.entity.question;

import java.io.Serializable;

/**
 * digw创建于17-6-13.
 * QuestionRequestResult->Paper
 */

public class Paper implements Serializable{
    private int id;
    private long duration;
    private String paperName;
    private int personTotal;
    private int diffcult;
    private int questionCount;
    private int score;
    private int leftTime;
    private boolean vCompany;
    private boolean hasAppQuestionType;
    private boolean isIntelligentPaper;
    private double avgTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getPersonTotal() {
        return personTotal;
    }

    public void setPersonTotal(int personTotal) {
        this.personTotal = personTotal;
    }

    public int getDiffcult() {
        return diffcult;
    }

    public void setDiffcult(int diffcult) {
        this.diffcult = diffcult;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public boolean isvCompany() {
        return vCompany;
    }

    public void setvCompany(boolean vCompany) {
        this.vCompany = vCompany;
    }

    public boolean isHasAppQuestionType() {
        return hasAppQuestionType;
    }

    public void setHasAppQuestionType(boolean hasAppQuestionType) {
        this.hasAppQuestionType = hasAppQuestionType;
    }

    public boolean isIntelligentPaper() {
        return isIntelligentPaper;
    }

    public void setIntelligentPaper(boolean intelligentPaper) {
        isIntelligentPaper = intelligentPaper;
    }

    public double getAvgTotal() {
        return avgTotal;
    }

    public void setAvgTotal(double avgTotal) {
        this.avgTotal = avgTotal;
    }
}
