package com.bridgelabz.questionbank.Model;

import java.util.List;

public class Questions {
    private Long questionId;

    private String question;
    private  questionType questionType;

    private questionLevels questionLevels;
    private List<String>questionOption;

    private List<String> correctAnswer;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Questions.questionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Questions.questionType questionType) {
        this.questionType = questionType;
    }

    public List<String> getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(List<String> questionOption) {
        this.questionOption = questionOption;
    }

    public Questions.questionLevels getQuestionLevels() {
        return questionLevels;
    }

    public void setQuestionLevels(Questions.questionLevels questionLevels) {
        this.questionLevels = questionLevels;
    }



    public enum questionType{
        SINGLE_CHOICE,
       MULTIPLE_CHOICE,
       FILL_IN_THE_BLANK
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public List<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(List<String> correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public enum questionLevels{
        EASY,
        MEDIUM,
        HARD
    }
}
