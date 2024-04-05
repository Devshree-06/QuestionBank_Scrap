package com.bridgelabz.questionbank.Model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "Topics")
public class Topics {

    private ObjectId id;

    private String topic;

    List<Subtopics> subtopic=new ArrayList<>();
    List<Questions>questions;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Subtopics> getSubtopic() {
        return subtopic;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public void setSubtopic(List<Subtopics> subtopic) {
        this.subtopic = subtopic;
    }

    public void addSubTopic(Subtopics stp) {
        this.subtopic.add(stp);
    }

    @Override
    public String toString() {
        return "Topics{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", subtopic=" + subtopic +
                '}';
    }

    public void addEasyQuestion(Questions question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        question.setQuestionLevels(Questions.questionLevels.EASY);
        questions.add(question);
    }

    public void addMediumQuestion(Questions question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        question.setQuestionLevels(Questions.questionLevels.MEDIUM);
        questions.add(question);
    }

    public void addHardQuestion(Questions question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        question.setQuestionLevels(Questions.questionLevels.HARD);
        questions.add(question);
    }
}
