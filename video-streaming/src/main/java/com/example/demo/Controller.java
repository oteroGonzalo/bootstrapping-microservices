package com.example.demo;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/video")
@Configuration
public class Controller {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private Queue queue;
    @Autowired
    private ResourceLoader resourceLoader;
    private final Resource resource;
    private static final String FORMAT = "classpath:videos/%s.mp4";
    String VIDEO_STORAGE_PORT = System.getenv("VIDEO_STORAGE_PORT");
    String VIDEO_STORAGE_HOST = System.getenv("VIDEO_STORAGE_HOST");
    String HISTORY_HOST = System.getenv("HISTORY_HOST");
    String HISTORY_PORT = System.getenv("HISTORY_PORT");

    public Controller(ResourceLoader resourceLoader, RestClient.Builder restClientBuilder) {
      //  RestClient restClientStorage = restClientBuilder.baseUrl(String.format("http://%s:%s", VIDEO_STORAGE_HOST, VIDEO_STORAGE_PORT)).build();
      //  this.restClientHistory = restClientBuilder.baseUrl(String.format("http://%s:%s", HISTORY_HOST, HISTORY_PORT)).build();
        String videoPath = "samplevideo.mp4";
        this.resource = new ClassPathResource("video-streaming/src/main/resources/video/" + videoPath);
    }
    public void sendViewedMessage(String videoPath) {
        template.convertAndSend("spring-boot", videoPath);
    }


    @GetMapping(path = "/1", produces = "video/mp4")
    public ResponseEntity<Resource> streamVideo(@RequestParam String id, @RequestHeader Map<String, String> headers) throws IOException {

        if (!headers.containsKey("range")) {
            try {
                sendViewedMessage(id);
            } catch (Exception ex) {
                System.out.println("unable to connect to history service");
            }
        }
        // String result = persistenceLayer.findSomething("hola");
        //   String result = persistenceLayer.findSomething("123");
        //    Optional<VideoData> videoDataOptional = videoRepository.findById(id);


        return ResponseEntity.status(HttpStatus.OK).header("Accept-Ranges", "bytes").header("Content-Type", "video/mp4").body(resource);
//        return videoDataOptional.map(videoData -> this.restClientStorage.get().uri("/video?videoId={id}", videoData.videoPath).retrieve().toEntity(Resource.class))
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
