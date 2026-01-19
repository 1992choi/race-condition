package com.example.racecondition.database.pessimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessi")
@RequiredArgsConstructor
public class ProductPessiController {

    private final ProductPessiService service;

    private final ProductPessiOrchestratorService orchestratorService;

    @PostMapping("/stock")
    public Long postStock() {
        return service.increaseStock();
    }

    @PostMapping("/stock-redisson")
    public Long postStockRedisson() {
        return orchestratorService.increaseStockWithRedisson();
    }

}
