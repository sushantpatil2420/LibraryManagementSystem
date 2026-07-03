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
}
