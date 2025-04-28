package com.example.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface InMemoryVideoRepository extends CrudRepository<VideoData, String> {
}
