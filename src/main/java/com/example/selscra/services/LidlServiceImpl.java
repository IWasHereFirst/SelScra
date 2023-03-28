package com.example.selscra.services;

import com.example.selscra.dto_lidl.DBProduct;
import com.example.selscra.dto_lidl.Product;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.lidl.Lidl;
import com.example.selscra.repositories.LidlCategoryRepository;
import com.example.selscra.repositories.LidlDBProductRepository;
import com.example.selscra.repositories.LidlProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LidlServiceImpl implements LidlService {

    @Autowired
    private LidlProductRepository lidlProductRepository;
    @Autowired
    private LidlCategoryRepository lidlCategoryRepository;
    @Autowired
    private LidlDBProductRepository lidlDBProductRepository;

    @Override
    public List<Category> newExtract() {

        Lidl lidl = new Lidl(true);
        List<Category> category = lidl.getCurrentDiscounts();
        List<Product> product = new ArrayList<>();
        category.forEach(cat -> {
            product.addAll(cat.getProductList());
        });
        lidlCategoryRepository.saveAll(category);
        lidlProductRepository.saveAll(product);
        return category;
    }

    @Override
    public Product addProductFromUrl(String url) {
        Category category = Lidl.addProductFromUrl(url);
        Product product = category.getProductList().get(0);
        lidlCategoryRepository.save(category);
        return lidlProductRepository.save(product);
    }

    @Override
    public void refreshPrices() {
        List<DBProduct> oldPrices = lidlDBProductRepository.findAll();
        int i = 0;
        for(DBProduct oldProd : oldPrices){

            Product currentProd = new Product();
            try{
                Lidl.urlProduct(new Category(), currentProd, oldProd.getUrl());
            } catch (Exception e){
                return;
            }
            System.out.println("new prod: " + currentProd);
            if (oldProd.getFullPrice() != currentProd.getFullPrice()){
                lidlProductRepository.save(currentProd);
            }
            if(i == 20) break;
            i++;
        }
    }
}
