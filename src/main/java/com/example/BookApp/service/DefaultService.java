package com.example.BookApp.service;

import com.example.BookApp.model.*;
import com.example.BookApp.repository.Book2GenreRepository;
import com.example.BookApp.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultService {

    private final TagRepository tagRepository;

    private final Book2GenreRepository book2GenreRepository;

    private final ModelMapper modelMapperToBook2Genre;

    DefaultService(ModelMapper modelMapperToBook2Genre, TagRepository tagRepository, Book2GenreRepository book2GenreRepository) {
        this.tagRepository = tagRepository;
        this.book2GenreRepository = book2GenreRepository;
        this.modelMapperToBook2Genre = modelMapperToBook2Genre;
    }

    public List<Tag> getAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    public String getTagNameByTagId(Integer tagId) {
        return tagRepository.findTagByTagId(tagId);
    }

    public TreeMap<Integer,Book2GenreWithCount> getBooksGenreLikeTreeMap() {
        TreeMap<Integer, Book2GenreWithCount> treeMap = new TreeMap();
        List<Book2GenreWithCountInterface> book2GenreWithCountInterfaces = book2GenreRepository.findBook2GenreWhereParentIdIsNull();
        List<Book2GenreWithCountInterface> book2GenreWithCountInterfacesChild = book2GenreRepository.findBook2GenreWhereParentIdIsNotNull();

        List<Book2GenreWithCount> listParentsForTreeMap = book2GenreWithCountInterfaces.stream()
                .map(this::convertToBook2GenreWithCount)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        List<Book2GenreWithCount> listChildren = book2GenreWithCountInterfacesChild.stream()
                .map(this::convertToBook2GenreWithCount)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        for (Book2GenreWithCount book2GenreWithCount : listParentsForTreeMap) {
            treeMap.put(book2GenreWithCount.getId(), new Book2GenreWithCount(book2GenreWithCount.getId(),
                    book2GenreWithCount.getParentId(),
                    book2GenreWithCount.getName(),
                    book2GenreWithCount.getBookCount(), null));
        }

        for (Book2GenreWithCount e : listChildren) {
            if (treeMap.containsKey(e.getParentId())) {
                if (treeMap.get(e.getParentId()).getList() == null) {
                    treeMap.get(e.getParentId()).setList(new TreeSet<>(new Book2GenreWithCountComparator()));
                }
                treeMap.get(e.getParentId()).getList().add(e);
            } else {
                findInTreeMap(treeMap, e);
            }
        }
        return treeMap;
    }

    private void findInTreeMap(TreeMap<Integer, Book2GenreWithCount> treeMap, Book2GenreWithCount e) {
        for (Book2GenreWithCount ob : treeMap.values()) {
            addToList(ob.getList(), e);
        }
    }

    private void addToList(TreeSet<Book2GenreWithCount> list, Book2GenreWithCount e) {
        if (list != null) {
            for (Book2GenreWithCount ob : list) {
                if (ob.getId().equals(e.getParentId())) {
                    if (ob.getList() == null) {
                        ob.setList(new TreeSet<>(new Book2GenreWithCountComparator()));
                    }
                    ob.getList().add(e);
                } else {
                    addToList(ob.getList(), e);
                }
            }
        }
    }

    private Book2GenreWithCount convertToBook2GenreWithCount(Book2GenreWithCountInterface book2GenreWithCountInterface) {
        return book2GenreWithCountInterface == null ? null : modelMapperToBook2Genre.map(book2GenreWithCountInterface, Book2GenreWithCount.class);
    }

}
