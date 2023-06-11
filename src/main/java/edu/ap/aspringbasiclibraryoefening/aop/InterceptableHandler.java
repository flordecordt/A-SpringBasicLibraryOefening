package edu.ap.aspringbasiclibraryoefening.aop;

import javax.persistence.Convert;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Console;

@Aspect
@Component
public class InterceptableHandler {
    @Autowired
    private edu.ap.aspringbasiclibraryoefening.redis.RedisService service;

    private static final int MAX_BOOKS = 5;

    @Around("@annotation(edu.ap.aspringbasiclibraryoefening.aop.Interceptable) && execution(String *(..))")
    public String checkMaxBooks(ProceedingJoinPoint joinPoint) throws Throwable {

        String currentBookCount = service.getKey("bookscount");
        int bookCount = 0; // Default value in case the string is null

        if (currentBookCount != null) {
            try {
                bookCount = Integer.parseInt(currentBookCount)-1;
            } catch (NumberFormatException e) {
                bookCount = 0;
            }
        }
        System.out.println(bookCount);
        String returnMessage = null;

        if (bookCount >= MAX_BOOKS) {
            System.out.println("You reached the max amount of books");
            returnMessage = "redirect:/homemax";
        } else {
            returnMessage = (String) joinPoint.proceed(); // Proceed with the method execution and get the return value
        }

        return returnMessage;
    }
}
