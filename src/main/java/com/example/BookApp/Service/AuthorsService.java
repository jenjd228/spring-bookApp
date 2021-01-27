package com.example.BookApp.Service;

import com.example.BookApp.model.Author;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorsService {

    private final Logger logger = Logger.getLogger(AuthorsService.class);

    private final JdbcTemplate jdbcTemplate;

    AuthorsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, List<Author>> getCharAndAuthorsLists() {
        List<Author> authors = jdbcTemplate.query("select * from authors", (ResultSet resultSet, int rowNum) -> {
            Author author = new Author();
            author.setId(resultSet.getInt("id"));
            author.setFio(resultSet.getString("fio"));
            author.setBiography(resultSet.getString("biography"));
            return author;
        });

        return authors.stream().collect(Collectors.groupingBy(t -> t.getFio().substring(0, 1)));
    }
}