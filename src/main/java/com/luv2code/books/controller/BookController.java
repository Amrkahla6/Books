package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import org.springframework.web.bind.annotation.*;

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
                new Book("Title 0ne","Author 1", "science"),
                new Book("Title Two","Author 2", "science"),
                new Book("Title Three","Author 3", "history"),
                new Book("Title Four","Author 4", "history"),
                new Book("Title Five","Author 5", "Math"),
                new Book("Title Six","Author 6", "Math")
              )
        );
    }

    @GetMapping("/api/books")
    public List<Book> books(@RequestParam(required = false) String category){

        if (category == null){
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title){
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody Book newBook){
        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if (isNewBook){
            books.add(newBook);
        }
    }

    @PutMapping("/api/books/{title}")
    public void updateBook(@PathVariable String title,@RequestBody Book updatedBook){
        for (int i =0; i < books.size(); i++){
            if (books.get(i).getTitle().equalsIgnoreCase(title)){
                books.set(i,updatedBook);
                return;            }
        }
    }
}
