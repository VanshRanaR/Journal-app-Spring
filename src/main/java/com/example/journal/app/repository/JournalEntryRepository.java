package com.example.journal.app.repository;

import com.example.journal.app.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {



}
//controller--> service--> repository
