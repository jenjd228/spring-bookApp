package com.example.BookApp.service;

import com.example.BookApp.model.Author;
import com.example.BookApp.repository.AuthorRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorsService {

    private final Logger logger = Logger.getLogger(AuthorsService.class);

    private final AuthorRepository authorRepository;

    @Autowired
    AuthorsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Map<String, List<Author>> getCharAndAuthorsLists() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().collect(Collectors.groupingBy(t -> t.getSlug().substring(0, 1)));
    }
}