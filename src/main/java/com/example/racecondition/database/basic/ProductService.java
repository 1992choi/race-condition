package com.example.racecondition.database.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ReentrantLock lock = new ReentrantLock();

    // 4.8. 코드
//    public Long increaseStock() {
//        ProductEntity productEntity = productRepository.findById(1L).get();
//        productEntity.setStock(productEntity.getStock() + 1);
//        ProductEntity save = productRepository.save(productEntity);
//
//        return save.getStock();
//    }

    // 4.9. 코드 (synchronized 사용)
//    public synchronized Long increaseStock() {
//        ProductEntity productEntity = productRepository.findById(1L).get();
//        productEntity.setStock(productEntity.getStock() + 1);
//        ProductEntity save = productRepository.save(productEntity);
//
//        return save.getStock();
//    }

    // 4.9. 코드 (ReentrantLock 사용)
    public Long increaseStock() {
        lock.lock();
        ProductEntity productEntity = productRepository.findById(1L).get();
        productEntity.setStock(productEntity.getStock() + 1);
        ProductEntity save = productRepository.save(productEntity);
        lock.unlock();

        return save.getStock();
    }

}
