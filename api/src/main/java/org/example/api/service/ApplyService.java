package org.example.api.service;

import lombok.RequiredArgsConstructor;
import org.example.api.domain.Coupon;
import org.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ApplyService
{
    private final CouponRepository couponRepository;

    public void apply(Long userId)
    {
        long count = couponRepository.count();

        if(count > 100){
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
