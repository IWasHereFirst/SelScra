package com.example.selscra.services;

import com.example.selscra.dto_lidl.Product;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.lidl.Lidl;
import com.example.selscra.repositories.LidlCategoryRepository;
import com.example.selscra.repositories.LidlDiscountProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LidlServiceImpl implements LidlService {

    @Autowired
    private LidlDiscountProductsRepository lidlDiscountProductsRepository;
    @Autowired
    private LidlCategoryRepository lidlCategoryRepository;

    @Override
    public List<Category> newExtract() {

        Lidl lidl = new Lidl(true);
        List<Category> category = lidl.getCurrentDiscounts();
        List<Product> product = new ArrayList<>();
        category.forEach(cat -> {
            product.addAll(cat.getProductList());
        });
        lidlCategoryRepository.saveAll(category);
        lidlDiscountProductsRepository.saveAll(product);
        return category;
    }

    @Override
    public Product addProductFromUrl(String url) {
        Category category = Lidl.addProductFromUrl(url);
        Product product = category.getProductList().get(0);
        lidlCategoryRepository.save(category);
        return lidlDiscountProductsRepository.save(product);
    }
}
