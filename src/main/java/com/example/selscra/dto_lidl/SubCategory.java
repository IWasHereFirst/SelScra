package com.example.selscra.dto_lidl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubCategory {

    @Id
    private long id;
    private String name;
    private String availableFrom;
    private String url;
    private long catId;
    @Transient
    private List<DiscountProduct> wishlistProductList = new ArrayList<>();;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public List<DiscountProduct> getWishlistProductList() {
        return wishlistProductList;
    }

    public void addProductToSubmenu(DiscountProduct wishlistProduct) {
        this.wishlistProductList.add(wishlistProduct);
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availableFrom='" + availableFrom + '\'' +
                ", url='" + url + '\'' +
                ", catId=" + catId +
                ", wishlistProductList=" + wishlistProductList +
                '}';
    }
}
