package edu.ap.aspringbasiclibraryoefening.jpa;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "book")
@XmlRootElement
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private  long id;

    @Column(name = "col_isbn")
    private String isbn;

    @Column(name = "col_title")
    private String title;

    @Column(name = "col_authors")
    private String authors;

    public Book() {
    }

    public Book(String isbn, String title, String authors) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
    }

    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @XmlElement(name = "authors")

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
