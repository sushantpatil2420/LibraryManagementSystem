package com.project.library.repository;

import com.project.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // SEARCH BOOK BY TITLE
    List<Book> findByBookTitle(String bookTitle);

    // SEARCH BOOK BY ISBN
    Book findByIsbn(String isbn);

    // SEARCH BOOKS BY CATEGORY NAME
    List<Book> findByCategory_CategoryName(String categoryName);

    // SEARCH BOOKS BY AUTHOR NAME
    List<Book> findByAuthors_AuthorName(String authorName);

    // BOOKS WITH AT LEAST ONE COPY AVAILABLE
    List<Book> findByAvailableCopiesGreaterThan(int copies);
}
