package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Integer id;

    private String photo;

    private String name;

    private String firstDescription;

    private String secondDescription;
}
