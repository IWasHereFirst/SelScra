package com.example.selscra.repositories;

import com.example.selscra.dto_lidl.DBProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidlDBProductRepository extends JpaRepository<DBProduct, Long> {
}
