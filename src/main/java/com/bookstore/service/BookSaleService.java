package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookSale;
import com.bookstore.entity.DiscountPolicy;
import com.bookstore.repository.BookSaleRepository;
import com.bookstore.type.DiscountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookSaleService {
    private final BookService bookService;
    private final DiscountPolicyService discountPolicyService;
    private final BookSaleRepository bookSaleRepository;

    @Transactional(readOnly = true)
    public BookSale getOrThrow(Book book) {
        return bookSaleRepository.findByBook(book)
                .orElseThrow(() -> new RuntimeException("해당 책이 존재하지 않습니다."));
    }

    @Transactional
    public void registerBookSale(String title, DiscountType discountType, Long discountAmount) {
        Book book = bookService.getOrThrow(title);
        DiscountPolicy policy = discountPolicyService.registerDiscountPolicy(discountType, discountAmount);
        BookSale bookSale = new BookSale(book, policy);
        bookSaleRepository.save(bookSale);
    }
}
