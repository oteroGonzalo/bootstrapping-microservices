package com.example.demo;


import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class Controller {

    public static long getMinimumCost(List<Integer> arr) {
        // Write your code here
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.size(); i++) {
            map.put(arr.get(i), map.getOrDefault(arr.get(i), 0) + 1);
        }
        List<Integer> sortedKeyList = new ArrayList<>(map.keySet());
        sortedKeyList.sort(Comparator.comparingInt(map::get).reversed());

        List<Integer> sortedArr = new ArrayList<>();
        for (int i = 0; i < sortedKeyList.size(); i++) {
            for (int j = 0; j < map.get(sortedKeyList.get(i)); j++) {
                sortedArr.add(sortedKeyList.get(i));
            }
        }
        long cost = 0;
        Set<Integer> unique = new HashSet<>();
        for (int i = 0; i < sortedArr.size(); i++) {
            unique.add(sortedArr.get(i));
            cost += unique.size();
        }
        return cost;
    }


    @GetMapping("/video")
    public ResponseEntity<Resource> streamVideo(@RequestParam String videoId) throws IOException {


        String STORAGE_ACCESS_KEY = System.getenv("STORAGE_ACCESS_KEY");
        String STORAGE_ACCOUNT_NAME = System.getenv("STORAGE_ACCOUNT_NAME");
        StorageSharedKeyCredential storageSharedKeyCredential = new StorageSharedKeyCredential(STORAGE_ACCOUNT_NAME, STORAGE_ACCESS_KEY);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(String.format("https://%s.blob.core.windows.net/", STORAGE_ACCOUNT_NAME))
                .credential(storageSharedKeyCredential)
                .buildClient();


        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("videos");

        BlobClient blobClient = blobContainerClient.getBlobClient(videoId);

        BinaryData binaryData = blobClient.downloadContent();


        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(new ByteArrayResource(binaryData.toBytes()));
    }
}