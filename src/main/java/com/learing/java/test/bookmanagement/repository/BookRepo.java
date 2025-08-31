package com.learing.java.test.bookmanagement.repository;

import com.learing.java.test.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Integer> {
}
