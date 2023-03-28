package com.example.selscra.repositories;

import com.example.selscra.dto_lidl.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidlProductRepository extends JpaRepository<Product, Long> {
}
