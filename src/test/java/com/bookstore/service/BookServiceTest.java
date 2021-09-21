package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    public void setup() {
        bookService = new BookService(bookRepository);
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void getOrThrow() {
        Book book = new Book("토지", "박경리", 30000L);
        bookRepository.save(book);

        Book result = bookService.getOrThrow(book.getTitle());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(result.getPrice()).isEqualTo(book.getPrice());
    }

    @Test
    public void registerBook() {
        bookService.registerBook("토지", "박경리", 30000L);

        assertThat(bookRepository.findByTitle("박경리")).isNotNull();
    }
}
