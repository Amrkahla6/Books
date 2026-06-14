package com.luv2code.books.request;

public class BookRequest {

    private String title;
    private String author;
    private String category;
    private int rating;

    public BookRequest(String title, int rating, String category, String author) {
        this.title = title;
        this.rating = rating;
        this.category = category;
        this.author = author;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
