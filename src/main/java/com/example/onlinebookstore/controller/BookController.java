package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.entity.Book;

import com.example.onlinebookstore.service.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	Logger LOG = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping
	public List<Book> getAllBooks() {
		LOG.info("get all books api called");
		List<Book> books = bookService.getAllBooks();
		LOG.info("get all books api returning: {}", books);
		return books;

	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
		return ResponseEntity.ok(book);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Book createBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
		Book updatedBook = bookService.updateBook(id, bookDetails);
		return ResponseEntity.ok(updatedBook);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}
