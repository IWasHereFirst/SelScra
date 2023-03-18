package com.example.selscra.dto_lidl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    private long id;
    private String name;
    private String availableFrom;
    private String url;
    @Transient
    private List<Product> productList = new ArrayList<>();

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

    public List<Product> getProductList() {
        return productList;
    }

    public void addProductToSubmenu(Product wishlistProduct) {
        this.productList.add(wishlistProduct);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availableFrom='" + availableFrom + '\'' +
                ", url='" + url + '\'' +
                ", ProductList=" + productList +
                '}';
    }
}
