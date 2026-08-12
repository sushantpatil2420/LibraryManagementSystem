package com.project.library.service;

import com.project.library.entity.Book;
import com.project.library.exception.ResourceNotFoundException;
import com.project.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //GET ALL BOOKS (PAGINATED)
    public Page<Book> getAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    //GET BOOK BY ID
    public Book getBookById(Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));
    }

    // UPDATE BOOK
    public Book updateBook(Long bookId, Book updatedBook) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        existingBook.setBookTitle(updatedBook.getBookTitle());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setTotalCopies(updatedBook.getTotalCopies());
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setAuthors(updatedBook.getAuthors());

        return bookRepository.save(existingBook);
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

    // ALL AVAILABLE BOOKS (AT LEAST 1 COPY LEFT)
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

}