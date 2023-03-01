package com.example.selscra.lidl;

import com.example.selscra.dto_lidl.Product;

import java.util.ArrayList;
import java.util.List;

public class SubMenu {

    private String headline;
    private String availableFrom;
    private String link;
    private List<Product> productList;

    public SubMenu(String headline, String availableFrom, String link) {
        this.headline = headline;
        this.availableFrom = availableFrom;
        this.link = link;
        this.productList = new ArrayList<>();
    }

    public String getHeadline() {
        return headline;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public String getLink() {
        return link;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProductToSubmenu(Product product) {
        this.productList.add(product);
    }

    @Override
    public String toString() {
        return "SubMenu{" +
                "headline='" + headline + '\'' +
                ", availableFrom='" + availableFrom + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
