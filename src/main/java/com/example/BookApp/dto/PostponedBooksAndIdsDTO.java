package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostponedBooksAndIdsDTO {

    private List<PostponedBooksDTO> list;

    private List<Integer> listIds;
}
