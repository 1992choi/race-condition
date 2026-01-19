package com.example.racecondition.database.pessimistic;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductPessiOrchestratorService {

    private final ProductPessiService service;

    private final StringRedisTemplate redis;

    private final RedissonClient redisson;

    public Long increaseStockWithRedisson() {
        RLock lock = redisson.getLock("stockLock");
        try {
            if (lock.tryLock(30, 5, TimeUnit.SECONDS)) {
                Long l = service.increaseStock(); // DB에 재고 증가
                Thread.sleep(50);
                redis.opsForValue().set("pessi_stock", String.valueOf(l)); // redis에 마지막 값 저장
                return l;
            }
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
        return -1L;
    }

}
