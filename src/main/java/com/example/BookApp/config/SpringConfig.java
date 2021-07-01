package com.example.BookApp.config;

import com.example.BookApp.dto.*;
import com.example.BookApp.model.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Configuration
public class SpringConfig {

    @Bean
    public ModelMapper modelMapperToBookInitDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<BookInit, BookInitDTO> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().convertByteToBooleanIsBestseller(source.getIsBestseller());
                map().setStatus("ARCHIVED");
            }
        };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperToBookSlugDTO() {
        Converter<Book, BookSlugDTO> converter = context -> {
            BookSlugDTO bookSlugDTO = new BookSlugDTO();
            Book book = context.getSource();

            bookSlugDTO.setId(book.getId());
            bookSlugDTO.setSlug(book.getSlug());
            bookSlugDTO.setTitle(book.getTitle());
            bookSlugDTO.setImage(book.getImage());

            bookSlugDTO.setAuthors(book.getAuthors()
                    .stream()
                    .map(x-> new AuthorOnlyNameAndIdDTO(x.getId(), x.getName()))
                    .collect(Collectors.toSet()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            bookSlugDTO.setComments(book.getComments()
                    .stream()
                    .map(comment -> new CommentDTO(comment.getBookId(),new UserOnlyNameAndIdDTO(comment.getUserId(),
                                                            comment.getUser().getName()),
                                                    comment.getText(),
                                                    comment.getTime().format(formatter),0,
                                                    comment.getLikeCount(),
                                                    comment.getDislikeCount()))
                    .collect(Collectors.toSet()));

            bookSlugDTO.setIsBestsellerByConvertingByteToBoolean(book.getIsBestseller());
            bookSlugDTO.setRating(book.getRating());
            bookSlugDTO.setStatus("ARCHIVED");
            bookSlugDTO.setPrice(book.getPrice());
            bookSlugDTO.setDiscountPriceByDiscount(book.getPrice(), book.getDiscount());
            bookSlugDTO.setDescription(book.getDescription());
            bookSlugDTO.setTagSet(book.getTagSet());
            bookSlugDTO.setRatingValueCount(book.getRatingValueCount());

            bookSlugDTO.setOneQuantity(book.getOneQuantity());
            bookSlugDTO.setTwoQuantity(book.getTwoQuantity());
            bookSlugDTO.setThreeQuantity(book.getThreeQuantity());
            bookSlugDTO.setFourQuantity(book.getFourQuantity());
            bookSlugDTO.setFiveQuantity(book.getFiveQuantity());
            return bookSlugDTO;
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Book.class,BookSlugDTO.class).setConverter(converter);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperToBookIdAndRating() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<BookIdAndRatingInterface, BookIdAndRating> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
            }
        };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperToBook2Genre() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Book2GenreWithCountInterface, Book2GenreWithCount> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
            }
        };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperToPostponedBooksDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<PostponedBooksInterface, PostponedBooksDTO> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
               map().setBookId(source.getBookId());
               map().setPrice(source.getPrice());
               map().setImage(source.getImage());
               map().setTitle(source.getTitle());
               map().setRating(source.getRating());
               map().setDiscountPrice(source.getDiscountPrice());
            }
        };
        PropertyMap<PostponedBooksInterface, AuthorOnlyNameAndIdDTO> propertyMap2 = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getAuthorId());
                map().setName(source.getAuthorName());
            }
        };
        modelMapper.addMappings(propertyMap);
        modelMapper.addMappings(propertyMap2);
        return modelMapper;
    }

}
