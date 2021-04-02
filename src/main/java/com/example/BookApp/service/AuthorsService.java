package com.example.BookApp.service;

import com.example.BookApp.dto.AuthorDTO;
import com.example.BookApp.model.Author;
import com.example.BookApp.repository.AuthorRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        List<Author> authors = authorRepository.findAll();
        return authors.stream().collect(Collectors.groupingBy(t -> t.getSlug().substring(0, 1)));
    }

    public ResponseEntity getAuthor(Integer authorId){
        Author author = authorRepository.findAuthorById(authorId);
        if (author != null){
            return new ResponseEntity(getAuthorDTO(author), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private AuthorDTO getAuthorDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setPhoto(author.getPhoto());
        String text = author.getDescription();

        String firstSentences = text.substring(0,author.getDescription().indexOf(".")+1);
        String textWithoutFirstSentences = text.replaceFirst(firstSentences,"");
        String secondSentences = textWithoutFirstSentences.substring(0,textWithoutFirstSentences.indexOf(".")+1);

        authorDTO.setFirstDescription(firstSentences+secondSentences);
        authorDTO.setSecondDescription(textWithoutFirstSentences.replaceFirst(secondSentences,""));
        return authorDTO;
    }
}