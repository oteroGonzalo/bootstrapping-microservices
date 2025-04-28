package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;



@Service
@Profile("mongo")
public class PersistenceLayerMongoImpl implements PersistenceLayer {
    @Autowired
    VideoRepository videoRepository;


    @Override
    public String findSomething(String id) {
        VideoData videoData = new VideoData();
        videoData.videoPath = "hola";
        videoData.id = "123";
        VideoData videoData1 = videoRepository.findById("123").orElse(videoData);
        return "hola";
    }

    @Override
    public void addSomething(VideoData something) {

        videoRepository.save(something);
    }
}
