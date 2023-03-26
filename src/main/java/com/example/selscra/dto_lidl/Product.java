package com.example.selscra.dto_lidl;

import com.example.selscra.lidl.Lidl;

import javax.persistence.*;

import static com.example.selscra.lidl.Lidl.priceLabeler;

@Entity
@Table(name = "product")
public class Product {

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
    private double deletedPrice = 0D;
    private long catId;

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

    public double getDeletedPrice() {
        return deletedPrice;
    }

    public void setDeletedPrice(double deletedPrice) {
        this.deletedPrice = deletedPrice;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public Product initializeProduct(){
        if (price.getDiscount() == null || price.getDiscount().getPercentageDiscount() == 0) {
            this.fullPrice = price.getPrice();
        } else {
            this.fullPrice = price.getPrice();
            this.deletedPrice = price.getDiscount().getDeletedPrice();
            this.discountPercentage = price.getDiscount().getPercentageDiscount();
        }
        this.canonicalUrl = "https://www.lidl.sk" + canonicalUrl;
        return this;
    }

    public void priceInitializer(String price){
        if (price.equals("0.0\n-0%\n0.0")){
            this.fullPrice = 0D;
        } else {
            String[] divided = price.split("\n");
            if (divided.length > 1){
                this.deletedPrice = Double.parseDouble(divided[2]);
                // priceLabeler used, because otherwise it would add 150g as 150% discount
                this.discountPercentage = Integer.parseInt(priceLabeler(divided[1]));
                this.fullPrice = Double.parseDouble(divided[0]);
            } else {
                this.fullPrice = Double.parseDouble(divided[0]);
            }
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "erpNumber=" + erpNumber +
                ", fullTitle='" + fullTitle + '\'' +
                ", image='" + image + '\'' +
                ", canonicalUrl='" + canonicalUrl + '\'' +
                ", price=" + price +
                ", subTitle='" + subTitle + '\'' +
                ", fullPrice=" + fullPrice +
                ", discountPercentage=" + discountPercentage +
                ", deletedPrice=" + deletedPrice +
                ", catId=" + catId +
                '}';
    }
}

class Price {
    private double price;
    private Discount discount;

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
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}

class Discount {
    private int percentageDiscount;
    private double deletedPrice;

    public double getDeletedPrice() {
        return deletedPrice;
    }

    public void setDeletedPrice(double deletedPrice) {
        this.deletedPrice = deletedPrice;
    }

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
                ", deletedPrice=" + deletedPrice +
                '}';
    }
}