package com.example.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlinebookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findAll();
	Optional<Book> findById(Long id);
}

