package com.example.selscra.services;

import com.example.selscra.dto_lidl.WishlistProduct;
import com.example.selscra.dto_lidl.Category;

import java.util.List;

public interface LidlService {

    List<WishlistProduct> getAllWishlistProducts();
    String addAllWishlistProducts();
    void removeAllWishlistProducts();

    List<Category> newExtract();
}
