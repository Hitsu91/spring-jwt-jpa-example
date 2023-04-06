package com.antsrl.jwtexample.controller;

import com.antsrl.jwtexample.dto.request.BookRequest;
import com.antsrl.jwtexample.dto.response.BookDto;
import com.antsrl.jwtexample.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll(@RequestParam(required = false) Integer authorId) {
        if (authorId == null) {
            return ResponseEntity.ok(service.getAll());
        }

        return ResponseEntity.ok(service.getAllByAuthor(authorId));
    }

    @PostMapping
    public ResponseEntity<BookDto> add(@RequestBody BookRequest request) {
        return ResponseEntity.ok(service.add(request));
    }

}
