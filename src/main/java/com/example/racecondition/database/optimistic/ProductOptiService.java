package com.example.racecondition.database.optimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptiService {

    private final ProductOptiRepository repository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Long increaseStock(){
        ProductOptiEntity entity = repository.findById(1L).get();
        entity.setStock(entity.getStock() + 1);
        ProductOptiEntity save = repository.save(entity);
        return save.getStock();
    }

}
