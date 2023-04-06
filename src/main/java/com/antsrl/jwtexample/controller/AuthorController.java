package com.antsrl.jwtexample.controller;

import com.antsrl.jwtexample.dto.request.AuthorRequest;
import com.antsrl.jwtexample.dto.response.AuthorDto;
import com.antsrl.jwtexample.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService service;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<AuthorDto> add(@RequestBody AuthorRequest request) {
        return ResponseEntity.ok(service.add(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorDto> update(@PathVariable Integer id, @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
