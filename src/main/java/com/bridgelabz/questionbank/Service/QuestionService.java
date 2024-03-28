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
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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



    public Mono<Questions>addQuestionsforMain(TopicDto topicDto,Questions questions){
        return topicRepository.findById(topicDto.getId())

                .flatMap(topics -> {
                    topics.getQuestions().add(questions);
                    if(topics.getQuestions()!=null && topics.getQuestions().isEmpty()){
                        topics.getQuestions().addAll(topicDto.getQuestions());

                    }
                    return topicRepository.save(topics).thenReturn(questions);

                });
    }

    public Mono<Topics> updateTopic(TopicDto topicDto) {
        // Retrieve the existing topic from the database
        Mono<Topics> existingTopicMono = questionBankRepository.findById(topicDto.getId());

        // Use flatMap to handle asynchronous operations
        return existingTopicMono.flatMap(existingTopic -> {
            // Update the fields of the existing topic with the values from the DTO
            if (topicDto.getTitle() != null) {
                existingTopic.setTitle(topicDto.getTitle());
            }

            // Update subtopic names
            if (topicDto.getSubtopic() != null) {
                for (Subtopics subtopic : topicDto.getSubtopic()) {
                    boolean subtopicExists = false;
                    // Iterate through existing subtopics to find a match
                    for (Subtopics existingSubtopic : existingTopic.getSubtopic()) {
                        if (existingSubtopic.getName().equals(subtopic.getName())) {
                            // If a match is found, update the existing subtopic
                            existingSubtopic.setName(subtopic.getName());
                            subtopicExists = true;
                            break;
                        }
                    }
                    // If the subtopic does not exist, add it to the existing topic
                    if (!subtopicExists) {
                        existingTopic.addSubTopic(subtopic);
                    }
                }
            }

            // Save the updated topic back to the database
            return questionBankRepository.save(existingTopic);
        });
    }





}
