package com.gonzalo.history;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private final MongoDBRepository mongoDBRepository;

    public Controller(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    @PostMapping("/viewed")
    public ResponseEntity<Void> postVideoInfo(@RequestBody String videoPath) {
        View view = new View();
        view.setTitle(videoPath);
        mongoDBRepository.save(view);
        return ResponseEntity.ok().build();
    }
}
