package com.bridgelabz.questionbank.Model;

import java.util.ArrayList;
import java.util.List;

public class Subtopics {

    private String name;
    private List<Questions> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Questions> getQuestions() {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public void addQuestion(Questions question) {
        getQuestions().add(question);
    }

    @Override
    public String toString() {
        return "Subtopics{" +
                "name='" + name + '\'' +
                ", questions=" + questions +
                '}';
    }
}
