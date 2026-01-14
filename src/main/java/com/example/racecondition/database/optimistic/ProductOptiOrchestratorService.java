package com.example.racecondition.database.optimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
