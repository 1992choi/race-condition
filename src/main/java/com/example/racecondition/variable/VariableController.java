package com.example.racecondition.variable;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/variable")
@RequiredArgsConstructor
public class VariableController {

    private final VariableService variableService;

    @PostMapping("/stock")
    public Long postStock() {
        return variableService.increaseStock();
    }

    @GetMapping("/stock")
    public Long getStock() {
        return variableService.getStock();
    }

    @PostMapping("/stock-atomic")
    public Long postStockAtomic() {
        return variableService.increaseStockAtomic();
    }

    @GetMapping("/stock-atomic")
    public Long getStockAtomic() {
        return variableService.getStockAtomic();
    }

}
