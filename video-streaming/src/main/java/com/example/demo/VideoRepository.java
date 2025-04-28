package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//@ConditionalOnProperty(name = "localdb")
@Profile("mongo")
public interface VideoRepository extends MongoRepository<VideoData, String> {
    public VideoData find(String id);
}



