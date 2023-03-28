package com.example.selscra.services;

import com.example.selscra.dto_lidl.Category;
import com.example.selscra.dto_lidl.Product;

import java.util.List;

public interface LidlService {

    List<Category> newExtract();

    Product addProductFromUrl(String url);

    void refreshPrices();
}
