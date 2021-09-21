package com.bookstore.repository;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookSaleRepository extends JpaRepository<BookSale, Long> {
    Optional<BookSale> findByBook(Book book);
}
