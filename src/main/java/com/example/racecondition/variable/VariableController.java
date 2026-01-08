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

    @PostMapping("/stock-reset")
    public void postStockReset() {
        variableService.resetStock();
    }

    @GetMapping("/stock")
    public Long getStock() {
        return variableService.getStock();
    }

}
