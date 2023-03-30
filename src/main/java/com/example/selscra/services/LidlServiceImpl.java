package com.example.selscra.services;

import com.example.selscra.dto_lidl.DBProduct;
import com.example.selscra.dto_lidl.Product;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.lidl.Lidl;
import com.example.selscra.repositories.LidlCategoryRepository;
import com.example.selscra.repositories.LidlDBProductRepository;
import com.example.selscra.repositories.LidlProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LidlServiceImpl implements LidlService {

    @Autowired
    private LidlProductRepository lidlProductRepository;
    @Autowired
    private LidlCategoryRepository lidlCategoryRepository;
    @Autowired
    private LidlDBProductRepository lidlDBProductRepository;

    @Override
    public ConcurrentHashMap<Category, Integer> newExtract() {

        Lidl lidl = new Lidl(true);
        ConcurrentHashMap<Category, Integer> category = lidl.getCurrentDiscounts();
        ConcurrentHashMap<Product, Integer> product = new ConcurrentHashMap<>();
        category.forEach((cat, m) -> {
            product.putAll(cat.getProductList());
        });
        lidlCategoryRepository.saveAll(category.keySet());
        lidlProductRepository.saveAll(product.keySet());
        return category;
    }

    @Override
    public Product addProductFromUrl(String url) {
        Category category = Lidl.addProductFromUrl(url);
        Product product = (Product) category.getProductList().keySet().toArray()[0];
        lidlCategoryRepository.save(category);
        return lidlProductRepository.save(product);
    }

    @Override
    public void refreshPrices() {
        int dbSize =  lidlDBProductRepository.findAll().size();
        AtomicInteger threadCount = new AtomicInteger();
        for (int i = 0; i <= dbSize/100; i++){
            int finalI = i;
            CompletableFuture.runAsync(()->{
                threadCount.getAndIncrement();
                List<DBProduct> oldPrices = lidlDBProductRepository.findAll(PageRequest.of(finalI, 100)).getContent();
                oldPrices.forEach(oldProd -> {
                    Product currentProd = new Product();
                    try{
                        Lidl.urlProduct(new Category(), currentProd, oldProd.getUrl());
                    } catch (Exception e){
                        return;
                    }
                    if (oldProd.getFullPrice() != currentProd.getFullPrice()){
                        lidlProductRepository.save(currentProd);
                    }
                });
            }).thenAccept(s -> {
                threadCount.getAndDecrement();
                System.out.println("Thread " + Thread.currentThread().getName() + " finished, number of running threads " + threadCount.get());
                if (threadCount.get() == 0) System.out.println("Job finished");
            });
        }
    }

    @Override
    public String deleteAll() {
        lidlProductRepository.deleteAll();
        return "All products were deleted";
    }

    /*
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
     */
}
