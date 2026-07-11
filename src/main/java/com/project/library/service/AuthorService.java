package com.project.library.service;

import com.project.library.entity.Author;
import com.project.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ADD ALL AUTHORS
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // GET ALL AUTHORS
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    // GET AUTHOR BY ID
    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);
    }

    // DELETE AUTHOR BY ID
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}