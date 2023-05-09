package com.example.dinosaurneil;

import java.time.LocalDateTime;

public class Feed {
    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime lastBuildDate;

    public Feed() {
       this.id = 0;
       this.title = "Untitled";
       this.lastBuildDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", lastUpdate=" + lastBuildDate +
                '}';
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastBuildDate(LocalDateTime lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }
}
