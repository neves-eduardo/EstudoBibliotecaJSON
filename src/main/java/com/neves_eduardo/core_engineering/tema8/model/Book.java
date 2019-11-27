package com.neves_eduardo.core_engineering.tema8.model;

import java.util.UUID;

public class Book {
    private UUID id;
    private String title;
    private String author;
    boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }



    public Book(String title, String author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book: " +
                "id:" + id +
                ", title:'" + title + '\'' +
                ", author:'" + author + '\'';
    }
}
