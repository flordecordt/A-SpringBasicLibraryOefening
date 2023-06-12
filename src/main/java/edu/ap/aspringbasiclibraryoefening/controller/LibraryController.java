package edu.ap.aspringbasiclibraryoefening.controller;

import edu.ap.aspringbasiclibraryoefening.jpa.Book;
import edu.ap.aspringbasiclibraryoefening.jpa.BookRepository;
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
    private BookRepository repository;

    @GetMapping ("/")
    public String RedirectHome(Model model){
        return "redirect:/searchBook";
    }
    @GetMapping ("/searchBook")
    public String searchPage(Model model){
        return "searchBook";
    }

    @PostMapping("/search")
    public String addBook(@RequestParam("title") String title){
       return "redirect:/home/" + title;
    }

    @GetMapping ("/home/{title}")
    public String findBooks(@PathVariable("title") String title, Model model) {
        model.addAttribute("books", repository.findByTitleContainsIgnoreCase(title));
        return "home";
    }





}
