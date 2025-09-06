package com.example.journal.app.repository;

import com.example.journal.app.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);


}
//controller--> service--> repository
