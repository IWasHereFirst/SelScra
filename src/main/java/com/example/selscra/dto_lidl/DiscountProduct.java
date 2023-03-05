package com.example.selscra.dto_lidl;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class DiscountProduct {

    @Id
    @Column(name = "id")
    private long erpNumber;
    @Column(name = "title")
    private String fullTitle;
    @Column(name = "imgUrl")
    private String image;
    @Column(name = "url")
    private String canonicalUrl;
    @Transient
    private Price price;
    private String subTitle = "";
    private double fullPrice = 0D;
    private int discountPercentage = 0;
    private double oldPrice = 0D;

    public long getErpNumber() {
        return erpNumber;
    }

    public void setErpNumber(long erpNumber) {
        this.erpNumber = erpNumber;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public DiscountProduct initializeProduct(){
        if (price.getDiscount() == null) {
            this.fullPrice = price.getPrice();
        } else {
            this.fullPrice = price.getOldPrice();
            this.oldPrice = price.getOldPrice();
            this.discountPercentage = price.getDiscount().getPercentageDiscount();
        }
        this.canonicalUrl = "https://www.lidl.sk" + canonicalUrl;
        return this;
    }

    @Override
    public String toString() {
        return "DiscountProduct{" +
                "erpNumber=" + erpNumber +
                ", fullTitle='" + fullTitle + '\'' +
                ", image='" + image + '\'' +
                ", canonicalUrl='" + canonicalUrl + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", fullPrice=" + fullPrice +
                ", discountPercentage=" + discountPercentage +
                ", oldPrice=" + oldPrice +
                '}';
    }
}

class Price {
    private double oldPrice;
    private double price;
    private Discount discount;

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Price{" +
                "oldPrice=" + oldPrice +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}

class Discount {
    private int percentageDiscount;

    public int getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(int percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "percentageDiscount=" + percentageDiscount +
                '}';
    }
}