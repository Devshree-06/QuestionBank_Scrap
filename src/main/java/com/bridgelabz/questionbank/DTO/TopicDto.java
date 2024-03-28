package com.bridgelabz.questionbank.DTO;

import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Subtopics;
import org.bson.types.ObjectId;

import java.util.List;

public class TopicDto {
    private ObjectId id;
    private String title;
    private List<Subtopics> subtopic;

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

    // Method to update subtopic names
    public void updateSubtopicNames(List<String> newNames) {
        if (subtopic != null && newNames != null && subtopic.size() == newNames.size()) {
            for (int i = 0; i < subtopic.size(); i++) {
                subtopic.get(i).setName(newNames.get(i));
            }
        }
    }

    @Override
    public String toString() {
        return "TopicDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtopics=" + subtopic +
                '}';
    }
}
