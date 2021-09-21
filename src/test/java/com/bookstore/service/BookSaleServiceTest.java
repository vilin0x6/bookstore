package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookSale;
import com.bookstore.entity.DiscountPolicy;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.BookSaleRepository;
import com.bookstore.repository.DiscountPolicyRepository;
import com.bookstore.type.DiscountType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookSaleServiceTest {
    @Autowired
    private BookSaleRepository bookSaleRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DiscountPolicyRepository discountPolicyRepository;

    private BookSaleService bookSaleService;


    @BeforeEach
    public void setup() {
        BookService bookService = new BookService(bookRepository);
        DiscountPolicyService discountPolicyService = new DiscountPolicyService(discountPolicyRepository);
        bookSaleService = new BookSaleService(bookService, discountPolicyService, bookSaleRepository);
    }

    @AfterEach
    public void tearDown() {
        bookSaleRepository.deleteAll();
        bookRepository.deleteAll();
        discountPolicyRepository.deleteAll();
    }

    @Test
    @Transactional
    public void getOrThrow() {
        Book book = new Book("토지", "박경리", 30000L);
        DiscountPolicy discountPolicy = new DiscountPolicy(DiscountType.PERCENT, 10L);
        bookRepository.save(book);
        discountPolicyRepository.save(discountPolicy);
        bookSaleRepository.save(new BookSale(book, discountPolicy));

        BookSale result = bookSaleService.getOrThrow(book);

        assertThat(result).isNotNull();
        assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getBook().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(result.getBook().getPrice()).isEqualTo(book.getPrice());
        assertThat(result.getDiscountPolicy().getDiscountType()).isEqualTo(discountPolicy.getDiscountType());
        assertThat(result.getDiscountPolicy().getAmount()).isEqualTo(discountPolicy.getAmount());
    }

    @Test
    public void registerBookSale() {
        Book book = new Book("토지", "박경리", 30000L);
        bookRepository.save(book);
        bookSaleService.registerBookSale("토지", DiscountType.AMOUNT, 3000L);

        assertThat(bookSaleRepository.findByBook(book)).isNotNull();
    }

}
