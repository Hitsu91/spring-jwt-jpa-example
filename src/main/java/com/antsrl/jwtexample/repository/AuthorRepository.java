package com.antsrl.jwtexample.repository;

import com.antsrl.jwtexample.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
