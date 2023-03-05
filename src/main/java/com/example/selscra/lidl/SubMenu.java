package com.example.selscra.lidl;

import com.example.selscra.dto_lidl.DiscountProduct;

import java.util.ArrayList;
import java.util.List;

public class SubMenu {

    private String headline;
    private String availableFrom;
    private String link;
    private List<DiscountProduct> wishlistProductList;

    public SubMenu(String headline, String availableFrom, String link) {
        this.headline = headline;
        this.availableFrom = availableFrom;
        this.link = link;
        this.wishlistProductList = new ArrayList<>();
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

    public List<DiscountProduct> getProductList() {
        return wishlistProductList;
    }

    public void addProductToSubmenu(DiscountProduct wishlistProduct) {
        this.wishlistProductList.add(wishlistProduct);
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
