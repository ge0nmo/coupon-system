package org.example.api.service;

import org.example.api.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest
{
    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void applyOnce()
    {
        applyService.apply(1L);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    void applyMultiple() throws InterruptedException
    {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++)
        {
            long userId = i;
            executorService.submit(() -> {
                try{
                   applyService.apply(userId);
               } finally
                {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100);
    }

    @Test
    void oneCouponForOneUser() throws InterruptedException
    {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++)
        {
            executorService.submit(() -> {
                try{
                    applyService.apply(1L);
                } finally
                {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);
        long count = couponRepository.count();
        assertThat(count).isEqualTo(1);
    }
}