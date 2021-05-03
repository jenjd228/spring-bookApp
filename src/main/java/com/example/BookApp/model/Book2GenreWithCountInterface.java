package com.example.BookApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public interface Book2GenreWithCountInterface {

    Integer getId();

    Integer getParentId();

    String getName();

    Integer getBookCount();

    List<Book2GenreWithCount> getList();

}
