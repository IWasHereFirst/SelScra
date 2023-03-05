package com.example.selscra.services;

import com.example.selscra.dto_lidl.DiscountProduct;
import com.example.selscra.dto_lidl.WishlistProduct;
import com.example.selscra.lidl.Lidl;
import com.example.selscra.repositories.LidlDiscountProductsRepository;
import com.example.selscra.repositories.LidlWishlistProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LidlServiceImpl implements LidlService {

    @Autowired
    private LidlDiscountProductsRepository lidlDiscountProductsRepository;
    @Autowired
    private LidlWishlistProductsRepository lidlWishlistProductsRepository;

    public List<WishlistProduct> getAllWishlistProducts() {
        return lidlWishlistProductsRepository.findAll();
    }

    @Override
    public String addAllWishlistProducts() {

        Lidl lidl = new Lidl(true);
        List<WishlistProduct> wishlistProducts = lidl.login().acceptCookies().getAllWishlistProducts();
        int nr = wishlistProducts.size();
        lidlWishlistProductsRepository.saveAll(wishlistProducts);
        return (nr > 1 ? (nr + " products ") : (nr + " product ")) + "were added";
    }

    @Override
    public void removeAllWishlistProducts() {
        lidlWishlistProductsRepository.deleteAll();
    }

    @Override
    public String addAllDiscountProducts() {
        Lidl lidl = new Lidl(true);
        List<DiscountProduct> discountProducts= lidl.getCurrentDiscounts();
        int nr = discountProducts.size();
        lidlDiscountProductsRepository.saveAll(discountProducts);
        return (nr > 1 ? (nr + " products ") : (nr + " product ")) + "were added";
    }
}
