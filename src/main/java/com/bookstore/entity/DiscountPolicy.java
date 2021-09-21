package com.bookstore.entity;

import com.bookstore.type.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * 할인 정책 Entity
 * 할인 타입과 액수를 가진다
 */
public class DiscountPolicy extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Long amount;

    public Long getDiscountAmount(Long price) {
        if(getDiscountType() == DiscountType.AMOUNT) {      // DiscountType이 AMOUNT인 경우
            return price -= amount;
        } else {                                            // DiscountType이 PERCENT인 경우
            Long tempPrice = Math.round(price * (1 - (amount * 0.01)));
            return tempPrice;
        }
    }
}
