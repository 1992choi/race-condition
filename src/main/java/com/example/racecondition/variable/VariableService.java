package com.example.racecondition.variable;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@Service
public class VariableService {

    // 3.4. 코드
//    private Long stock = 0L;
//
//    public void resetStock() {
//        stock = 0L;
//    }
//
//    public Long increaseStock() {
//        stock = stock + 1;
//        return stock;
//    }



    // 3.5. 코드 (synchronized 사용버전)
//    private Long stock = 0L;
//
//    public void resetStock() {
//        stock = 0L;
//    }
//
//    public synchronized Long increaseStock() {
//        stock = stock + 1;
//        return stock;
//    }



    // 3.5. 코드 (ReentrantLock 사용버전)
    private Long stock = 0L;

    private final ReentrantLock lock = new ReentrantLock();

    public void resetStock() {
        stock = 0L;
    }

    public Long increaseStock() {
        lock.lock();
        stock = stock + 1;
        lock.unlock();

        return stock;
    }

}
