package com.example.dinosaurneil;

import com.example.dinosaurneil.util.StringToLocalDateTime;

import java.time.LocalDateTime;

public class FeedItem {
    private int id;
    private int parentId;
    private String title;
    private String link;
    private String description;
    private LocalDateTime pubDate;
    private String enclosure;

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

    public int getId() {
        return id;
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

    public String getLink() {
        return link;
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

    public LocalDateTime getPubDate() {
        return pubDate;
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
