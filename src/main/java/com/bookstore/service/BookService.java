package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Book getOrThrow(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("해당 제목의 책이 존재하지 않습니다."));
    }

    @Transactional
    public void registerBook(String title, String author, Long price) {
        if(bookRepository.findByTitle(title).isPresent()) {
            throw new RuntimeException("해당 책이 이미 존재합니다.");
        } else {
            Book book = new Book(title, author, price);
            bookRepository.save(book);
        }
    }
}
