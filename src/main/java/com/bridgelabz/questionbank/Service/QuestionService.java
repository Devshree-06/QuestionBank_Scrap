package com.bridgelabz.questionbank.Service;


import com.bridgelabz.questionbank.DTO.TopicDto;
//import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Subtopics;
import com.bridgelabz.questionbank.Model.Topics;
import com.bridgelabz.questionbank.Repository.QuestionBankRepository;
//import com.bridgelabz.questionbank.Repository.QuestionRepository;
import com.bridgelabz.questionbank.Repository.TopicRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class QuestionService {

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private TopicRepository topicRepository;

    public Mono<Topics> addMainTopic(Topics topic){
        return questionBankRepository.save(topic);
    }

    public Flux<Topics> getAllTopics(){
        return questionBankRepository.findAll();
    }

    public Mono<Topics>getTopicById(ObjectId id){
        return questionBankRepository.findById(id);
    }


public Flux<Questions> addQuestionWithLevel(TopicDto topicDto, Questions question) {
    // Check if the topic exists, if not create a new topic
    return topicRepository.findById(topicDto.getId())
            .switchIfEmpty(createNewTopic(topicDto))
            .flatMapMany(topic -> {
                // Set the question level using the enum
                question.setQuestionLevels(question.getQuestionLevels());

                // Find the subtopic to add the question to
                Subtopics subtopic = findOrCreateSubtopic(topic, topicDto.getSubtopicName());

                // Add the question to the subtopic
                subtopic.addQuestion(question);



                // Save the updated topic with the new question and return the saved question
                return topicRepository.save(topic)
                        .thenMany(Flux.just(question));
            });
}

    private Mono<Topics> createNewTopic(TopicDto topicDto) {
        Topics newTopic = new Topics();
        newTopic.setTopic(topicDto.getTopic());

        // Set subtopics if available
        if (topicDto.getSubtopic() != null) {
            newTopic.setSubtopic(topicDto.getSubtopic());
        }

        // Set questions if available
        if (topicDto.getQuestions() != null) {
            newTopic.setQuestions(topicDto.getQuestions());
        } else {
            // Initialize questions list if null
            newTopic.setQuestions(new ArrayList<>());
        }

        // Save the new topic
        return topicRepository.save(newTopic);
    }

    private Subtopics findOrCreateSubtopic(Topics topic, String subtopicName) {
        // Check if the subtopic already exists
        Subtopics subtopic = topic.getSubtopic().stream()
                .filter(st -> st.getName().equals(subtopicName))
                .findFirst()
                .orElse(null);

        // If the subtopic doesn't exist, create a new one
        if (subtopic == null) {
            subtopic = new Subtopics();
            subtopic.setName(subtopicName);
            topic.addSubTopic(subtopic);
        }


        return subtopic;
    }


    public Mono<Topics> updateTopic(TopicDto topicDto) {
        // Directly chain the operations using flatMap
        return questionBankRepository.findById(topicDto.getId())
                .flatMap(existingTopic -> {
                    // Update the fields of the existing topic with the values from the DTO
                    if (topicDto.getTopic() != null) {
                        existingTopic.setTopic(topicDto.getTopic());
                    }
                    // Update subtopic names using nested streams
                    topicDto.getSubtopic().forEach(subtopic -> {
                        // Check if the subtopic already exists
                        boolean subtopicExists = existingTopic.getSubtopic().stream()
                                .anyMatch(existingSubtopic -> existingSubtopic.getName().equals(subtopic.getName()));

                        // If subtopic exists, update its name; otherwise, add it to the existing topic
                        if (subtopicExists) {
                            existingTopic.getSubtopic().stream()
                                    .filter(existingSubtopic -> existingSubtopic.getName().equals(subtopic.getName()))
                                    .findFirst()
                                    .ifPresent(existingSubtopic -> existingSubtopic.setName(subtopic.getName()));
                        } else {
                            existingTopic.addSubTopic(subtopic);
                        }
                    });
                    // Save the updated topic back to the database
                    return questionBankRepository.save(existingTopic);
                });
    }


}
