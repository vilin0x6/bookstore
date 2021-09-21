package com.bookstore.service;

import com.bookstore.entity.DiscountPolicy;
import com.bookstore.repository.DiscountPolicyRepository;
import com.bookstore.type.DiscountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiscountPolicyService {
    private final DiscountPolicyRepository discountPolicyRepository;

    @Transactional
    public DiscountPolicy registerDiscountPolicy(DiscountType discountType, Long discountAmount) {
        DiscountPolicy policy = new DiscountPolicy(discountType, discountAmount);
        return discountPolicyRepository.save(policy);
    }
}
