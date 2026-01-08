package com.example.racecondition.variable;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class VariableService {

    private Long stock = 0L;

    public void resetStock() {
        stock = 0L;
    }

    public Long increaseStock() {
        stock = stock + 1;
        return stock;
    }

}
