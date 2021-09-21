package com.bookstore.entity;

import com.bookstore.type.DiscountType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookSaleTest {

    @Test
    public void getPrice() {
        Book book = new Book("토지", "박경리", 30000L);
        DiscountPolicy discountPolicy = new DiscountPolicy(DiscountType.PERCENT, 10L);
        BookSale bookSale = new BookSale(book, discountPolicy);

        Long result = bookSale.getPrice();

        assertThat(result).isEqualTo(27000L);
    }
}
