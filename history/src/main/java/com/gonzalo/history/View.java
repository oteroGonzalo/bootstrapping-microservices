package com.gonzalo.history;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class View {
    @Id
    private String id;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
