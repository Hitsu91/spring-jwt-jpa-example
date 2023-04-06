package com.antsrl.jwtexample.repository;

import com.antsrl.jwtexample.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByAuthor_Id(Integer authorId);
}
