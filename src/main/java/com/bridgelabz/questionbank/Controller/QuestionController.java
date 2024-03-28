package com.bridgelabz.questionbank.Controller;


import com.bridgelabz.questionbank.DTO.TopicDto;
//import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Subtopics;
import com.bridgelabz.questionbank.Model.Topics;
import com.bridgelabz.questionbank.Service.QuestionService;
import com.bridgelabz.questionbank.Util.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/topic")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/main")
    public Mono<ResponseEntity<Topics>> addMainTopic(@RequestBody Topics topic){
        return questionService.addMainTopic(topic)
                .map(createdTopic -> ResponseEntity.status(HttpStatus.CREATED).body(createdTopic));
    }


    @GetMapping("/alltopics")
    public Flux<ResponseEntity<Topics>> getAllTopics(){
        return questionService.getAllTopics()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Topics>>getTopicById(@PathVariable ObjectId id){
        return questionService.getTopicById(id)
                .map(topic->ResponseEntity.status(HttpStatus.CREATED).body(topic))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/updatetopic")
    public Mono<ResponseEntity<Topics>>updateTopic(@RequestBody TopicDto topicDto){
         return questionService.updateTopic(topicDto)
                .map(newtopic->ResponseEntity.status(HttpStatus.CREATED).body(newtopic))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping("/addquestion/{id}")
    public Mono<ResponseEntity<Questions>>addQuestion(@RequestBody Questions question,TopicDto topicDto){
        return questionService.addQuestionsforMain(topicDto,question)
                .map(savedques->ResponseEntity.ok().body(savedques));

    }



}
