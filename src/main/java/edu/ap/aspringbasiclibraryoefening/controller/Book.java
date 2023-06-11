package edu.ap.aspringbasiclibraryoefening.controller;

import javax.persistence.*;


public class Book {
    private String isbn;
    private String title;
    private String authors;

    public Book() {
    }

    public Book(String isbn, String title, String authors) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
