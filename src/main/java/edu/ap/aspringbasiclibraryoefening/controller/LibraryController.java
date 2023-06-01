package edu.ap.aspringbasiclibraryoefening.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import edu.ap.aspringbasiclibraryoefening.aop.Interceptable;
import edu.ap.aspringbasiclibraryoefening.jpa.Book;
import edu.ap.aspringbasiclibraryoefening.jpa.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Controller
@Scope("session")
public class LibraryController {
    @Autowired
    private BookRepository repository;

    @RequestMapping("/")
    public String RedirectHome(Model model){
        return "redirect:/home";
    }
    @RequestMapping("/home")
    @Interceptable
    public String getHomepage(Model model){
        model.addAttribute("books", repository.findAll());
        model.addAttribute("maxbooks", false);
        return "home";
    }

    @RequestMapping("/homemax")
    public String getHomepageMax(Model model){
        model.addAttribute("books", repository.findAll());
        model.addAttribute("maxbooks", true);
        return "home";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repository.delete(book);
        return "redirect:/home";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam("title") String title, Model model) {
        model.addAttribute("books", repository.findBooksByTitleContains(title.trim()));
        return "home";
    }

    @RequestMapping("/rentBookPage")
    @Interceptable
    public String getRentBook(){
        return "rentBook";
    }

    @PostMapping("/rentBook")
    public String addBook(@RequestParam("isbn") String isbn, @RequestParam("title") String title) {
        isbn = isbn.trim();
        title = title.trim();
        try {
            // save the new person
            Book newBook = new Book(isbn, title);
            this.repository.save(newBook);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "redirect:/home";
    }



}
