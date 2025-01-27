package com.j2e.library.dto.mapper;

import com.j2e.library.dto.BookDto;
import com.j2e.library.entity.Book;

import java.util.ArrayList;

public class BookMapper {
    public static BookDto toDto(Book book){
        return new BookDto(
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable()
        );
    }

    public static Book toEntity(BookDto dto){
        return new Book(
                null,
                dto.getTitle(),
                dto.getAuthor(),
                dto.isAvailable(),
                new ArrayList<>()
        );
    }

}
