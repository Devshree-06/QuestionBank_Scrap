package com.bridgelabz.questionbank.Repository;

import com.bridgelabz.questionbank.Model.Topics;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TopicRepository extends ReactiveMongoRepository<Topics, ObjectId> {


}
