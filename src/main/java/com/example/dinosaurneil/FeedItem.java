package com.example.dinosaurneil;

import java.time.LocalDateTime;

public class FeedItem {
    private int id;
    private int parentId;
    private String title;
    private String link;
    private String description;
    private LocalDateTime pubDate;
    private String enclosure;

    public FeedItem() {
        this.id = 0;
        this.parentId = 0;
        this.title = "Untitled";
        this.description = "";
        this.pubDate = LocalDateTime.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    @Override
    public String toString() {
        return "FeedItem{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                ", enclosure='" + enclosure + '\'' +
                '}';
    }
}
