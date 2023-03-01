package com.example.selscra.dto_lidl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String subTitle = "";
    private String imgUrl = "";
    private String url = "";
    private double fullPrice = 0D;
    private int discountPercentage = 0;
    private double discountPrice = 0D;

    public Product(String title, String price) {
        this.title = title;
        priceInitializer(price);
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    private void priceInitializer(String price){
        if (price.equals("0.0\n-0%\n0.0")){
            this.fullPrice = 0D;
        } else {
            String[] divided = price.split("\n");
            if (divided.length > 1){
                this.discountPrice = Double.parseDouble(divided[0]);
                this.discountPercentage = Integer.parseInt(divided[1]);
                this.fullPrice = Double.parseDouble(divided[2]);
            } else {
                this.fullPrice = Double.parseDouble(divided[0]);
            }
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "\n\ttitle='" + title + '\'' +
                "\n\tsubTitle='" + subTitle + '\'' +
                "\n\timgURL='" + imgUrl + '\'' +
                "\n\turl='" + url + '\'' +
                "\nfullPrice=" + fullPrice +
                ", discountPercentage=" + discountPercentage +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
