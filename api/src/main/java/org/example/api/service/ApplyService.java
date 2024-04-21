package org.example.api.service;

import lombok.RequiredArgsConstructor;
import org.example.api.domain.Coupon;
import org.example.api.producer.CouponCreateProducer;
import org.example.api.repository.AppliedUserRepository;
import org.example.api.repository.CouponCountRepository;
import org.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ApplyService
{
    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public void apply(Long userId)
    {
        Long apply = appliedUserRepository.add(userId);

        if(apply != 1)
            return;

        long count = couponCountRepository.increment();

        if(count > 100)
            return;


        couponCreateProducer.create(userId);
    }
}
