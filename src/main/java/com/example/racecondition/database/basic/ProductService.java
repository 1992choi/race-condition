package com.example.racecondition.database.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Long increaseStock() {
        ProductEntity productEntity = productRepository.findById(1L).get();
        productEntity.setStock(productEntity.getStock() + 1);
        ProductEntity save = productRepository.save(productEntity);

        return save.getStock();
    }

}
