package com.example.racecondition.database.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
