package com.project.library.controller;

import com.project.library.entity.Book;
import com.project.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book saveBook(@Valid @RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping
    public Page<Book> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Long bookId){
        return bookService.getBookById(bookId);
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @Valid @RequestBody Book book){
        return bookService.updateBook(bookId, book);
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

    // SEARCH BOOKS BY CATEGORY
    @GetMapping("/category/{categoryName}")
    public List<Book> searchBooksByCategory(@PathVariable String categoryName) {
        return bookService.searchBookByCategory(categoryName);
    }

    // SEARCH BOOKS BY AUTHOR
    @GetMapping("/author/{authorName}")
    public List<Book> searchBookByAuthor(@PathVariable String authorName) {
        return bookService.searchBookByAuthor(authorName);
    }

    // ALL AVAILABLE BOOKS
    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }
}
