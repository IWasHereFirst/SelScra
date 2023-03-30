package com.example.selscra.services;

import com.example.selscra.dto_lidl.Category;
import com.example.selscra.dto_lidl.Product;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface LidlService {

    ConcurrentHashMap<Category, Integer> newExtract();
    //List<Category> newExtract();

    Product addProductFromUrl(String url);

    void refreshPrices();

    String deleteAll();

}
