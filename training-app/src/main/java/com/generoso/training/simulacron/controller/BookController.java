package com.generoso.training.simulacron.controller;

import com.generoso.training.simulacron.model.Book;
import com.generoso.training.simulacron.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookRepository repository;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        var books = repository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable String id) {
        var book = repository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book.get());
    }

    @PostMapping
    public ResponseEntity<Book> post(@RequestBody Book book) {
        var newBook = repository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> put(@PathVariable String id, @RequestBody Book book) {
        var existingOptionalBook = repository.findById(id);
        if (existingOptionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var existingBook = existingOptionalBook.get();
        existingBook.setIsbn(book.getIsbn());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublisher(book.getPublisher());
        repository.save(existingBook);
        return ResponseEntity.ok(existingBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable String id) {
        var book = repository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repository.delete(book.get());
        return ResponseEntity.noContent().build();
    }
}
