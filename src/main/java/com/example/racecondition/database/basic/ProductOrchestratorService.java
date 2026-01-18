package com.example.racecondition.database.basic;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ProductOrchestratorService {

    private final ProductService productService;

    private final ReentrantLock lock = new ReentrantLock();

    public /*synchronized*/ Long increaseStockWithTran() {
        lock.lock();
        Long l = productService.increaseStockWithTran();
        lock.unlock();
        return l;
    }

    public Long increaseStockWithIsolation() {
        while (true) {
            try {
                return productService.increaseStockWithIsolation();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private final RedissonClient redissonClient;

    public Long increaseStockWithRedisson() {
        RLock lock = redissonClient.getLock("lock");

        try {
            if (lock.tryLock(10, 5, TimeUnit.SECONDS)) {
                return productService.increaseStockWithRedisson();
            }
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }

        return -1L;
    }

}
