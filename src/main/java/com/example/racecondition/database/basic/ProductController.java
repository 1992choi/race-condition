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

    @PostMapping("/stock")
    public Long postStock() {
        return productService.increaseStock();
    }

}
