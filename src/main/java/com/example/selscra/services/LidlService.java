package com.example.selscra.services;

import com.example.selscra.dto_lidl.Product;

import java.util.List;

public interface LidlService {

    List<Product> getAllWishlistProducts();
    String addAllWishlistProducts();
    void removeAllWishlistProducts();
}
