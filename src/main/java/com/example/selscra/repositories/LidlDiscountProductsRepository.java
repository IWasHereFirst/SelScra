package com.example.selscra.repositories;

import com.example.selscra.dto_lidl.DiscountProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidlDiscountProductsRepository extends JpaRepository<DiscountProduct, Long> {
}
