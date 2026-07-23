package com.project.library.service;

import com.project.library.entity.Author;
import com.project.library.exception.ResourceNotFoundException;
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

    // ADD NEW AUTHORS
    public Author saveAuthor(Author author) {

        return authorRepository.save(author);
    }

    // GET ALL AUTHORS
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    // GET AUTHOR BY ID
    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found"));
    }

    // DELETE AUTHOR BY ID
    public void deleteAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found");
        }
        authorRepository.deleteById(authorId);
    }
}