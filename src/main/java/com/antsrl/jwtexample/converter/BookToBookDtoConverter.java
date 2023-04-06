package com.antsrl.jwtexample.converter;

import com.antsrl.jwtexample.dto.response.BookDto;
import com.antsrl.jwtexample.entity.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookToBookDtoConverter implements Function<Book, BookDto> {

    @Override
    public BookDto apply(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getName());
    }
}
