package com.example.racecondition.database.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductOrchestratorService productOrchestratorService;

    @PostMapping("/stock")
    public Long postStock() {
        return productService.increaseStock();
    }

    @PostMapping("/stock-tran")
    public Long postStockTran() {
        return productOrchestratorService.increaseStockWithTran();
    }

    @PostMapping("/stock-isolation")
    public Long postStockIsolation() {
        return productOrchestratorService.increaseStockWithIsolation();
    }

    @PostMapping("/stock-distributed")
    public Long postStockDistributedSystem() {
        return productService.increaseStockWithDistributedSystem();
    }

    @PostMapping("/stock-redisson")
    public Long postStockRedisson() {
        return productOrchestratorService.increaseStockWithRedisson();
    }

}
