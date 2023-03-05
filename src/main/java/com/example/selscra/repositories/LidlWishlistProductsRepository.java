package com.example.selscra.repositories;

import com.example.selscra.dto_lidl.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidlWishlistProductsRepository extends JpaRepository<WishlistProduct, Long> {
}
