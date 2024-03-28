package com.bridgelabz.questionbank.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "Topics")
public class Topics {

    private ObjectId id;

    private String title;

    List<Subtopics> subtopic=new ArrayList<>();
    List<Questions>questions;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                ", title='" + title + '\'' +
                ", subtopic=" + subtopic +
                '}';
    }
}
