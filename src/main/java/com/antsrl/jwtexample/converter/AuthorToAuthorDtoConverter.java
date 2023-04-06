package com.antsrl.jwtexample.converter;

import com.antsrl.jwtexample.dto.response.AuthorDto;
import com.antsrl.jwtexample.entity.Author;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AuthorToAuthorDtoConverter implements Function<Author, AuthorDto> {
    @Override
    public AuthorDto apply(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
