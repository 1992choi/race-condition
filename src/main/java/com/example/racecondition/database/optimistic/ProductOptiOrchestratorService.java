package com.example.racecondition.database.optimistic;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductOptiOrchestratorService {

    private final ProductOptiService service;

    public Long increaseStockWithOpti() {
        while (true) {
            try {
                return service.increaseStock();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private final StringRedisTemplate redis;
    private final RedissonClient redisson;

    public Long increaseStockWithRedisson() {
        RLock lock = redisson.getLock("lock");
        while (true) {
            try {
                if (lock.tryLock(10, 5, TimeUnit.SECONDS)) {
                    // 비지니스 로직 (redis에 저장)
                    redis.opsForValue().increment("stock");
                    return service.increaseStock();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                lock.unlock();
            }
        }
    }

}
