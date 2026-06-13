package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(
                new Book("Title 0ne","Author 1", "Category one"),
                new Book("Title Two","Author 2", "Category Two"),
                new Book("Title Three","Author 3", "Category Three"),
                new Book("Title Four","Author 4", "Category Four"),
                new Book("Title Five","Author 5", "Category Five"),
                new Book("Title Six","Author 6", "Category Six")
              )
        );
    }

    @GetMapping("/api/books")
    public List<Book> books(){
        return books;
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title){
        for (Book book : books){
            if (book.getTitle().equalsIgnoreCase(title)){
                return book;
            }
        }

        return null;
    }
}
