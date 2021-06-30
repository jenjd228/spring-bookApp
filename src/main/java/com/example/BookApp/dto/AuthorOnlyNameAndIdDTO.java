package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorOnlyNameAndIdDTO {

    private Integer id;

    private String name;
}
