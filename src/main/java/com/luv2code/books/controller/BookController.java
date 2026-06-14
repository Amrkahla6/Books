package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import com.luv2code.books.request.BookRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(
                new Book(1, "Computer Science Pro", "Chad Darby", "Computer Science", 5),
                new Book(2, "Java Spring Master", "Eric Roby", "Computer Science", 5),
                new Book(3, "Why 1+1 Rocks", "Adil A.", "Math", 5),
                new Book(4, "How Bears Hibernate", "Bob B.", "Science", 2),
                new Book(5, "A Pirate's Treasure", "Curt C.", "History", 3),
                new Book(6, "Why 2+2 is Better", "Dan D.", "Math", 1)
              )
        );
    }

    @GetMapping
    public List<Book> books(@RequestParam(required = false) String category){

        if (category == null){
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id){
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String createBook(@RequestBody BookRequest bookRequest){
        int id = books.isEmpty() ? 1 : Math.toIntExact(books.getLast().getId() + 1);

        convertToBook(id, bookRequest);

        return "The book has been created";
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable int id,@RequestBody Book updatedBook){
        for (int i =0; i < books.size(); i++){
            if (books.get(i).getId() == id){
                books.set(i,updatedBook);
                return;            }
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id){
        books.removeIf(book -> book.getId() == id);
        return "Deleted Successfully";
    }

    private void convertToBook(int id, BookRequest bookRequest){
        Book book = new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );

        books.add(book);
    }
}
