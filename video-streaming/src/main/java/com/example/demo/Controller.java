package com.example.demo;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/video")
public class Controller {


    private final ResourceLoader resourceLoader;

    public Controller(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/1")
    public ResponseEntity<Resource> streamVideo() throws IOException {

        InputStream inputStream = resourceLoader.getResource("classpath:\\video\\SampleVideo_1280x720_10mb.mp4").getInputStream();
        byte[] data = inputStream.readAllBytes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(new ByteArrayResource(data));
    }

}
