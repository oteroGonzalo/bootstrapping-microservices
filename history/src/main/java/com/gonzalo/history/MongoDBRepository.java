package com.gonzalo.history;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBRepository extends MongoRepository<View, String> {
}
