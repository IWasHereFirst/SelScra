package com.example.selscra.services;

import com.example.selscra.dto_lidl.DiscountProduct;
import com.example.selscra.dto_lidl.SubCategory;
import com.example.selscra.dto_lidl.WishlistProduct;
import com.example.selscra.lidl.Lidl;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.repositories.LidlCategoryRepository;
import com.example.selscra.repositories.LidlDiscountProductsRepository;
import com.example.selscra.repositories.LidlSubCategoryRepository;
import com.example.selscra.repositories.LidlWishlistProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LidlServiceImpl implements LidlService {

    @Autowired
    private LidlDiscountProductsRepository lidlDiscountProductsRepository;
    @Autowired
    private LidlWishlistProductsRepository lidlWishlistProductsRepository;
    @Autowired
    private LidlCategoryRepository lidlCategoryRepository;
    @Autowired
    private LidlSubCategoryRepository lidlSubCategoryRepository;

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
    public List<Category> newExtract() {
        Lidl lidl = new Lidl(true);
        List<Category> category = lidl.getCurrentDiscounts();
        List<SubCategory> subCategory = new ArrayList<>();
        List<DiscountProduct> product = new ArrayList<>();
        category.forEach(cat -> {
            cat.getSubmenu().forEach(prod -> {
                product.addAll(prod.getWishlistProductList());
            });
            subCategory.addAll(cat.getSubmenu());
        });
        lidlCategoryRepository.saveAll(category);
        lidlSubCategoryRepository.saveAll(subCategory);
        lidlDiscountProductsRepository.saveAll(product);
        return category;
    }

    @Override
    public void addProductFromUrl(String url) {
        DiscountProduct product =  Lidl.addProductFromUrl(url);
        lidlDiscountProductsRepository.save(product);
    }
}
