package edu.ap.aspringbasiclibraryoefening;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import edu.ap.aspringbasiclibraryoefening.jpa.Book;
import edu.ap.aspringbasiclibraryoefening.jpa.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

@SpringBootApplication
public class ASpringBasicLibraryOefeningApplication implements CommandLineRunner{
    @Value("classpath:static/books.json")
    public InputStream data;

    @Autowired
    private BookRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(ASpringBasicLibraryOefeningApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Book[] books = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JaxbAnnotationModule());
        try {
            books = objectMapper.readValue(data, Book[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Book book : books) {
            repository.save(book);
        }
    }
}
