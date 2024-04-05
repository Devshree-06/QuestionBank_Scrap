package com.bridgelabz.questionbank.Controller;


import com.bridgelabz.questionbank.DTO.TopicDto;
//import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Questions;
import com.bridgelabz.questionbank.Model.Subtopics;
import com.bridgelabz.questionbank.Model.Topics;
import com.bridgelabz.questionbank.Service.QuestionService;
import com.bridgelabz.questionbank.Util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@RestController
@Api(tags="Question Controller")
@RequestMapping("/topic")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/main")
    @ApiOperation(value="Adding main topic",response=Topics.class)
    public Mono<ResponseEntity<Topics>> addMainTopic(@RequestBody Topics topic){
        return questionService.addMainTopic(topic)
                .map(createdTopic -> ResponseEntity.status(HttpStatus.CREATED).body(createdTopic));
    }


//    @RequestMapping(value = "/")
//    public void redirect(HttpServletResponse response) throws IOException {
//        response.sendRedirect("swagger-ui.html");
//    }
//@GetMapping("/")
//public Mono<Void> redirect(ServerHttpResponse response) {
//    response.setStatusCode(HttpStatus.SEE_OTHER);
//    response.getHeaders().setLocation("/swagger-ui.html");
//    return response.setComplete();
//}

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


//    @PatchMapping("/addquestion/{id}")
//    public Mono<ResponseEntity<TopicDto>>addQuestion(@RequestBody Questions question,@PathVariable ObjectId id){
//        return questionService.addQuestionsforMain(topicDto,id)
//                .map(ResponseEntity::ok);
//
//    }
@PostMapping("/add-question")
public Mono<ResponseEntity<List<Questions>>> addQuestionWithLevel(
        @RequestBody TopicDto topicDto,
        @RequestParam("questionLevel") String questionLevel) {

    // Parse the question level string into the enum value
    Questions.questionLevels level = Questions.questionLevels.valueOf(questionLevel.toUpperCase());

    // Retrieve the list of questions from the topic DTO
    List<Questions> questionsList = topicDto.getQuestions();

    if (questionsList == null) {
        // Handle the case where questionsList is null
        return Mono.just(ResponseEntity.badRequest().build());
    }

    // Process each question individually and collect the responses into a List
    return Flux.fromIterable(questionsList)
            .flatMap(question -> {
                // Set the question level for each question
                question.setQuestionLevels(level);
                // Call the service method to add the question
                return questionService.addQuestionWithLevel(topicDto, question);
            })
            .collectList()
            .map(savedQuestions -> ResponseEntity.ok(savedQuestions));
}



}