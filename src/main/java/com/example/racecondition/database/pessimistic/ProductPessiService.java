package com.example.racecondition.database.pessimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductPessiService {

    private final ProductPessiRepository repository;

    @Transactional
    public Long increaseStock() {
        ProductPessiEntity entity = repository.findByName("apple").get();
        entity.setStock(entity.getStock() + 1);
        ProductPessiEntity save = repository.save(entity);
        return save.getStock();
    }

}
