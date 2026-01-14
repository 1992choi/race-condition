package com.example.racecondition.database.optimistic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptiRepository extends JpaRepository<ProductOptiEntity, Long> {
}
