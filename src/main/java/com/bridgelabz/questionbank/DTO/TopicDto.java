package com.bridgelabz.questionbank.DTO;

import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Subtopics;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TopicDto {
    private ObjectId id;
    private String topic;
    private List<Subtopics> subtopic;

    private List<Questions>questions;
    private String questionType; // Add this field
    private String questionLevel;


    public TopicDto() {

    }

    public TopicDto( List<Questions> questions) {
        this.questions = questions;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }

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
        if (questions == null) {
            questions = new ArrayList<>();
        }
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
    // Method to get the name of the subtopic
    public String getSubtopicName() {
        if (subtopic != null && !subtopic.isEmpty()) {
            return subtopic.get(0).getName(); // Assuming there's only one subtopic
        }
        return null;
    }

    @Override
    public String toString() {
        return "TopicDto{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", subtopics=" + subtopic +
                '}';
    }


}
