package com.example.BookApp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharAndAuthorsList {

    private String firstCharFio;

    private List<Author> authors;
}
