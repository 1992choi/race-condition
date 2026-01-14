package com.example.racecondition.database.optimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/opti")
@RequiredArgsConstructor
public class ProductOptiController {

    private final ProductOptiOrchestratorService service;

    @PostMapping("/stock")
    public Long postStock() {
        return service.increaseStockWithOpti();
    }

}
