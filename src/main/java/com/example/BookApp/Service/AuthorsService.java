package com.example.BookApp.Service;

import com.example.BookApp.model.Author;
import com.example.BookApp.repository.AuthorRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        List<Author> authors = null;
        System.out.println(authorRepository);
        Optional<Author> author = authorRepository.findById(2);
        System.out.println(author.isPresent());
        return authors.stream().collect(Collectors.groupingBy(t -> t.getSlug().substring(0, 1)));
    }
}