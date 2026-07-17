package com.project.library.service;

import com.project.library.entity.Book;
import com.project.library.exception.ResourceNotFoundException;
import com.project.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Add NEW BOOK
    public Book saveBook(Book book){
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    //GET ALL BOOKS
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    //GET BOOK BY ID
    public Book getBookById(Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));
    }

    //DELETE BOOK BY ID
    public void deleteBook(Long bookId){
        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found");
        }
        bookRepository.deleteById(bookId);
    }

    // SEARCH BOOK BY TITLE
    public List<Book> searchBookByTitle(String bookTitle) {
        return bookRepository.findByBookTitle(bookTitle);
    }

    // SEARCH BOOK BY ISBN
    public Book searchBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    // SEARCH BOOKS BY CATEGORY NAME
    public List<Book> searchBookByCategory(String categoryName) {
        return bookRepository.findByCategory_CategoryName(categoryName);
    }

    // SEARCH BOOKS BY AUTHOR NAME
    public List<Book> searchBookByAuthor(String authorName) {
        return bookRepository.findByAuthors_AuthorName(authorName);
    }

}