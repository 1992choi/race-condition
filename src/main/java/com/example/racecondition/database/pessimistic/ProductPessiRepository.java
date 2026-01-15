package com.example.racecondition.database.pessimistic;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductPessiRepository extends JpaRepository<ProductPessiEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductPessiEntity> findByName(String name);

}
