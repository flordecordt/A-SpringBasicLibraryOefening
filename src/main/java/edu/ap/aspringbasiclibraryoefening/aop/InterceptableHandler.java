package edu.ap.aspringbasiclibraryoefening.aop;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import edu.ap.aspringbasiclibraryoefening.jpa.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Aspect
@Component
public class InterceptableHandler {
    @Autowired
    private BookRepository repository;

    private static final int MAX_BOOKS = 5;

    @Around("@annotation(edu.ap.aspringbasiclibraryoefening.aop.Interceptable) && execution(String *(..))")
    public String checkMaxBooks(ProceedingJoinPoint joinPoint) throws Throwable {
        int currentBookCount = (int) repository.count();
        String returnMessage = null;

        if (currentBookCount >= MAX_BOOKS) {
            System.out.println("You reached the max amount of books");
            returnMessage = "redirect:/homemax";
        } else {
            returnMessage = (String) joinPoint.proceed(); // Proceed with the method execution and get the return value
        }

        return returnMessage;
    }
}
