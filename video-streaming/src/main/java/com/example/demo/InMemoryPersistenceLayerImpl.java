package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Profile("local")
public class InMemoryPersistenceLayerImpl implements PersistenceLayer {


  public InMemoryVideoRepository inMemoryVideoRepository;

    public InMemoryPersistenceLayerImpl(InMemoryVideoRepository inMemoryVideoRepository) {
        this.inMemoryVideoRepository = inMemoryVideoRepository;
    }


    @Override
    public String findSomething(String id) {
        Optional<VideoData> result = inMemoryVideoRepository.findById("123");
        if (result.isPresent()) return result.get().videoPath;
        return "not found";
    }

    @Override
    public void addSomething(VideoData something) {
        VideoData videoData = new VideoData();
        videoData.videoPath = "LocalContent added";
        videoData.id = "123";
        inMemoryVideoRepository.save(videoData);
    }
}
