package com.example.dinosaurneil;

import java.time.LocalDateTime;

public class FeedItem {
    public int id;
    public int parentId;
    public String title;
    public String link;
    public String description;
    public LocalDateTime pubDate;
    public String enclosure;

    public FeedItem(int id, int parentId, String title, String link, String description, String pubDate) {

    }

    public FeedItem(int id, int parentId, String title, String link, String description, String pubDate, String enclosure) {

    }
}
