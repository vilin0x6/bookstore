package com.bookstore.repository;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    Optional<BookInventory> findByBook(Book book);
}
