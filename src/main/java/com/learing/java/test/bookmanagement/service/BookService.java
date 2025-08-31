package com.learing.java.test.bookmanagement.service;
import com.learing.java.test.bookmanagement.model.Book;
import com.learing.java.test.bookmanagement.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepo repo;
    public BookService(BookRepo repo) {
        this.repo = repo;
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public Book getBookById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Book addBook(Book book) {
        return repo.save(book);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        Book book = repo.findById(id).orElse(null);
        if (book == null) return null;
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        return repo.save(book);
    }

    public void deleteBook(Integer id) {
        repo.deleteById(id);
    }
}