package com.antsrl.jwtexample.service;

import com.antsrl.jwtexample.converter.AuthorToAuthorDtoConverter;
import com.antsrl.jwtexample.dto.request.AuthorRequest;
import com.antsrl.jwtexample.dto.response.AuthorDto;
import com.antsrl.jwtexample.entity.Author;
import com.antsrl.jwtexample.exception.ResourceNotFoundException;
import com.antsrl.jwtexample.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final AuthorToAuthorDtoConverter authorToAuthorDtoConverter;

    public List<AuthorDto> getAll() {
        return repository.findAll().stream().map(authorToAuthorDtoConverter).toList();
    }

    public AuthorDto add(AuthorRequest request) {
        var newAuthor = Author.builder().name(request.name()).build();
        return authorToAuthorDtoConverter.apply(repository.save(newAuthor));
    }

    public AuthorDto update(Integer authorId, AuthorRequest request) {
        var existingAuthor = repository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Impossibile modificare authore inesistente"));

        existingAuthor.setName(request.name());
        repository.save(existingAuthor);

        return authorToAuthorDtoConverter.apply(existingAuthor);
    }

    public void delete(Integer authorId) {
        repository.deleteById(authorId);
    }

}
