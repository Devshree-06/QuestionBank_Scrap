package com.bridgelabz.questionbank.Repository;

import com.bridgelabz.questionbank.Model.Topics;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface QuestionBankRepository extends ReactiveMongoRepository <Topics,String>{

    Mono<Topics> findById(ObjectId id);
}
