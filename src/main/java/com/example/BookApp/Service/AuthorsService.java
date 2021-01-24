package com.example.BookApp.Service;

import com.example.BookApp.Model.Author;
import com.example.BookApp.Model.CharAndAuthorsList;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class AuthorsService {
    //select DISTINCT substring(fio,0,1) from authors
    private final Logger logger = Logger.getLogger(AuthorsService.class);

    private final JdbcTemplate jdbcTemplate;

    AuthorsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getOnlyChar() {
        List<String> strings = jdbcTemplate.query("select DISTINCT substring(fio,0,1) as char from authors", (ResultSet resultSet, int rowNum) -> resultSet.getString("char"));
        strings.sort(Comparator.comparing(Object::toString));
        logger.info(Arrays.toString(strings.toArray()));
        return strings;
    }

    public List<CharAndAuthorsList> getCharAndAuthorsLists() {
        List<CharAndAuthorsList> charAndAuthorsLists = new ArrayList<>();
        for (String firstCharName : getOnlyChar()) {
            String query = "select * from authors where substring(fio,0,1) ='" + firstCharName + "'";
            List<Author> authors = jdbcTemplate.query(query, (ResultSet resultSet, int rowNum) -> {
                Author author = new Author();
                author.setId(resultSet.getInt("id"));
                author.setFio(resultSet.getString("fio"));
                author.setBiography(resultSet.getString("biography"));
                return author;
            });
            charAndAuthorsLists.add(new CharAndAuthorsList(firstCharName, authors));
        }
        logger.info(Arrays.toString(charAndAuthorsLists.toArray()));
        return charAndAuthorsLists;
    }
}
//"select * from authors where substring(fio,0,1) = A"