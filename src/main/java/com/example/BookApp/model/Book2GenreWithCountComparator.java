package com.example.BookApp.model;

import java.util.Comparator;

public class Book2GenreWithCountComparator implements Comparator<Book2GenreWithCount> {
    @Override
    public int compare(Book2GenreWithCount o1, Book2GenreWithCount o2) {
        return o2.getBookCount().compareTo(o1.getBookCount());
    }

}
