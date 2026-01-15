package com.example.racecondition.database.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
//    public Long increaseStock() {
//        lock.lock();
//        ProductEntity productEntity = productRepository.findById(1L).get();
//        productEntity.setStock(productEntity.getStock() + 1);
//        ProductEntity save = productRepository.save(productEntity);
//        lock.unlock();
//
//        return save.getStock();
//    }

    // 4.11. 코드 (@Transactional과 synchronized 혹은 ReentrantLock 같이 사용)
    @Transactional
    public Long increaseStock() {
        lock.lock();
        ProductEntity productEntity = productRepository.findById(1L).get();
        productEntity.setStock(productEntity.getStock() + 1);
        ProductEntity save = productRepository.save(productEntity);
        lock.unlock();

        return save.getStock();
    }

    // 4.12. 코드
    @Transactional
    public Long increaseStockWithTran() {
        ProductEntity productEntity = productRepository.findById(1L).get();
        productEntity.setStock(productEntity.getStock() + 1);
        ProductEntity save = productRepository.save(productEntity);

        return save.getStock();
    }

    // 4.13. 코드
    // @Transactional(isolation = Isolation.READ_COMMITTED)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Long increaseStockWithIsolation() {
        ProductEntity productEntity = productRepository.findById(1L).get();
        productEntity.setStock(productEntity.getStock() + 1);
        ProductEntity save = productRepository.save(productEntity);

        return save.getStock();
    }

}
