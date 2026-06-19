package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import com.luv2code.books.exceptions.BookErrorResponse;
import com.luv2code.books.exceptions.BookNotFoundException;
import com.luv2code.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Books Rest API Endpoints", description = "Operations related to books")
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

    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> books(@Parameter(description = "Optional query parameter")
                                @RequestParam(required = false) String category){

        if (category == null){
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Operation(summary = "Get a book by Id", description = "Retrieve a specific book by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of book to be retrieved")
                                @PathVariable @Min(value = 1) int id){
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(
                        () -> new BookNotFoundException("Book not found with id " + id)
                );
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createBook(@Valid @RequestBody BookRequest bookRequest){
        int id = books.isEmpty() ? 1 : Math.toIntExact(books.getLast().getId() + 1);

        convertToBook(id, bookRequest);

        return "The book has been created";
    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateBook(@Parameter(description = "Id of the book to update")
                               @PathVariable @Min(value = 1) int id,@Valid @RequestBody BookRequest bookRequest){
        for (int i =0; i < books.size(); i++){
            if (books.get(i).getId() == id){
                Book updatedBook = convertToBook(id,bookRequest);
                books.set(i,updatedBook);
                return;            }
        }
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String deleteBook(@Parameter(description = "Id of the book to delete")
                                 @PathVariable @Min(value = 1) int id){
        books.removeIf(book -> book.getId() == id);
        return "Deleted Successfully";
    }

    private Book convertToBook(int id, BookRequest bookRequest){
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleExceptions(BookNotFoundException exception){
        BookErrorResponse bookErrorResponse = new BookErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
    }
}
