package com.bookstore.entity;

import com.bookstore.type.DiscountType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookInventoryTest {

    @Test
    public void addBook() {
        Book book = new Book("토지", "박경리", 30000L);
        DiscountPolicy discountPolicy = new DiscountPolicy(DiscountType.PERCENT, 10L);
        BookSale bookSale = new BookSale(book, discountPolicy);

        BookInventory bookInventory = BookInventory.empty(book);
        bookInventory.addBook(bookSale);

        assertThat(bookInventory.getIncome()).isEqualTo(bookSale.getPrice());
        assertThat(bookInventory.getCount()).isEqualTo(1L);
    }

    @Test
    public void minusBook() {
        Book book = new Book("토지", "박경리", 30000L);
        DiscountPolicy discountPolicy = new DiscountPolicy(DiscountType.PERCENT, 10L);
        BookSale bookSale = new BookSale(book, discountPolicy);

        BookInventory bookInventory = BookInventory.empty(book);
        bookInventory.setCount(1L);
        bookInventory.setIncome(bookSale.getPrice());
        bookInventory.minusBook(bookSale);

        assertThat(bookInventory.getIncome()).isEqualTo(0L);
        assertThat(bookInventory.getCount()).isEqualTo(0L);
    }
}
