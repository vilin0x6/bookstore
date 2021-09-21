package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookInventory;
import com.bookstore.entity.BookSale;
import com.bookstore.entity.DiscountPolicy;
import com.bookstore.repository.BookInventoryRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.type.DiscountType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class BookInventoryServiceTest {

    @Autowired
    private BookInventoryRepository bookInventoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @Mock
    private BookSaleService bookSaleService;

    private BookInventoryService bookInventoryService;

    @BeforeEach
    public void setup() {
        bookInventoryService = new BookInventoryService(bookInventoryRepository, bookService, bookSaleService);
    }

    @AfterEach
    public void tearDown() {
        bookInventoryRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @Transactional
    public void storeBook() {
        Book book = new Book("토지", "박경리", 30000L);
        bookRepository.save(book);

        DiscountPolicy discountPolicy = new DiscountPolicy(DiscountType.PERCENT, 10L);
        BookSale bookSale = new BookSale(book, discountPolicy);
        given(bookService.getOrThrow("토지")).willReturn(book);
        given(bookSaleService.getOrThrow(book)).willReturn(bookSale);

        assertThat(bookInventoryRepository.count()).isEqualTo(0L);
        bookInventoryService.storeBook("토지");

        assertThat(bookInventoryRepository.count()).isEqualTo(1L);
        BookInventory result = bookInventoryRepository.findAll().get(0);
        assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getBook().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(result.getBook().getPrice()).isEqualTo(book.getPrice());
        assertThat(result.getCount()).isEqualTo(1L);
        assertThat(result.getIncome()).isEqualTo(27000L);
    }

    @Test
    @Transactional
    public void sellBook() {
        Book book = new Book("토지", "박경리", 30000L);
        bookRepository.save(book);

        bookInventoryRepository.save(new BookInventory(book, 1L, 27000L));

        DiscountPolicy discountPolicy = new DiscountPolicy(DiscountType.PERCENT, 10L);
        BookSale bookSale = new BookSale(book, discountPolicy);
        given(bookService.getOrThrow("토지")).willReturn(book);
        given(bookSaleService.getOrThrow(book)).willReturn(bookSale);

        bookInventoryService.sellBook("토지");

        assertThat(bookInventoryRepository.count()).isEqualTo(1L);
        BookInventory result = bookInventoryRepository.findAll().get(0);
        assertThat(result.getBook().getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getBook().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(result.getBook().getPrice()).isEqualTo(book.getPrice());
        assertThat(result.getCount()).isEqualTo(0L);
        assertThat(result.getIncome()).isEqualTo(0L);
    }
}
