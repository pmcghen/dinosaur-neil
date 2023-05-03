package com.example.dinosaurneil;

import com.example.dinosaurneil.util.StringToLocalDateTime;

import java.time.LocalDateTime;

public class FeedItem {
    public int id;
    public int parentId;
    public String title;
    public String link;
    public String description;
    public LocalDateTime pubDate;
    public String enclosure;

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

    public FeedItem(int id, int parentId, String title, String link, String description, String pubDate) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = StringToLocalDateTime.convertStringToDateTime(pubDate);
    }

    public FeedItem(int id, int parentId, String title, String link, String description, String pubDate, String enclosure) {

    }
}
