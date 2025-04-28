package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public interface PersistenceLayer {
  public String findSomething(String id);
  public void addSomething(VideoData something);

}
