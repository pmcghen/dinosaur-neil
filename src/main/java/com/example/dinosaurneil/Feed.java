package com.example.dinosaurneil;

import com.example.dinosaurneil.util.StringToLocalDateTime;

import java.time.LocalDateTime;

public class Feed {
    public int id;
    public String title;
    public String link;
    public String description;
    public LocalDateTime lastBuildDate;

    public Feed(int id, String title, String link, String description, String lastBuildDate) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.lastBuildDate = StringToLocalDateTime.convertStringToDateTime(lastBuildDate);
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
}
