package com.antsrl.jwtexample.service;

import com.antsrl.jwtexample.converter.BookToBookDtoConverter;
import com.antsrl.jwtexample.dto.request.BookRequest;
import com.antsrl.jwtexample.dto.response.BookDto;
import com.antsrl.jwtexample.entity.Book;
import com.antsrl.jwtexample.exception.ResourceNotFoundException;
import com.antsrl.jwtexample.repository.AuthorRepository;
import com.antsrl.jwtexample.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookToBookDtoConverter bookToBookDtoConverter;

    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(bookToBookDtoConverter).toList();
    }

    public List<BookDto> getAllByAuthor(Integer authorId) {
        return bookRepository.findAllByAuthor_Id(authorId).stream().map(bookToBookDtoConverter).toList();
    }

    public BookDto add(BookRequest bookRequest) {
        var authorId = bookRequest.authorId();
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + authorId + " not exists"));

        var newBook = Book.builder().name(bookRequest.name()).author(author).build();

        return bookToBookDtoConverter.apply(bookRepository.save(newBook));
    }

}
