package edu.ap.aspringbasiclibraryoefening.controller;

import edu.ap.aspringbasiclibraryoefening.aop.Interceptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@Scope("session")
public class LibraryController {
    @Autowired
    private edu.ap.aspringbasiclibraryoefening.redis.RedisService service;

    @GetMapping ("/")
    public String RedirectHome(Model model){
        return "redirect:/home";
    }
    @GetMapping ("/flush")
    public String flushDB(){
        service.flushDb();
        return "redirect:/home";
    }
    @GetMapping("/home")
    @Interceptable
    public String getHomepage(Model model){
        List<Book> bookList = new ArrayList<>();

        Set<String> books = service.keys("books:*");

        for (String m : books){
            List<String> authors = service.getList(m);
            String[] parts = m.split(":");

            String strAuthors = "";

            for(String a : authors){
                strAuthors += a + ", ";
            }
            strAuthors = strAuthors.substring(0, strAuthors.length() - 2);

            Book book = new Book(parts[1], parts[2], strAuthors);
            bookList.add(book);
        }

        model.addAttribute("books", bookList);
        model.addAttribute("maxbooks", false);
        return "home";
    }

    @GetMapping("/homemax")
    public String getHomepageMax(Model model){
        List<Book> bookList = new ArrayList<>();

        Set<String> books = service.keys("books:*");

        for (String m : books){
            List<String> authors = service.getList(m);
            String[] parts = m.split(":");

            String strAuthors = "";

            for(String a : authors){
                strAuthors += a + ", ";
            }
            strAuthors = strAuthors.substring(0, strAuthors.length() - 2);

            Book book = new Book(parts[1], parts[2], strAuthors);
            bookList.add(book);
        }

        model.addAttribute("books", bookList);
        model.addAttribute("maxbooks", true);
        return "home";
    }

    @GetMapping("/rentBookPage")
    @Interceptable
    public String getRentBook(){
        return "rentBook";
    }

    @PostMapping("/rentBook")
    public String addBook(@RequestParam("isbn") String isbn,
                          @RequestParam("title") String title,
                          @RequestParam("authors") String authors) {
        //genereer de sleutel op basis van isbn, title
        String key = "books:" + isbn + ":" + title;

        //De authors splitsen op basis van komma's
        String[] authorSplit = authors.split(",");

        //elke author toevoegen aan de redis lijst
        for (String author : authorSplit) {
            this.service.rpush(key, author);
        }
        // Verhoog het totale aantal films met 1
        this.service.incr("bookscount");

        return "redirect:/home";
    }

    @GetMapping ("/delete/{key}")
    public String deleteKey(@PathVariable("key") String key) {
        service.deleteKey(key);
        this.service.decr("bookscount");
        return "redirect:/home";
    }





}
