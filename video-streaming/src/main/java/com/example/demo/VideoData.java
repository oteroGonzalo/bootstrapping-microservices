package com.example.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document("videos")
@Entity
public class VideoData {
    @Id
    String id;
    String videoPath;
}
