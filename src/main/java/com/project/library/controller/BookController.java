package com.project.library.controller;

import com.project.library.entity.Book;
import com.project.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public Optional<Book> getBookById(@PathVariable Long bookId){
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
    }

    // SEARCH BOOKS BY TITLE
    @GetMapping("/title/{bookTitle}")
    public List<Book> searchBookByTitle(@PathVariable String bookTitle) {
        return bookService.searchBookByTitle(bookTitle);
    }

    // SEARCH BOOKS BY ISBN
    @GetMapping("/isbn/{isbn}")
    public Book searchBookByIsbn(@PathVariable String isbn) {
        return bookService.searchBookByIsbn(isbn);
    }
}
