package com.example.selscra.common;

public class Product {
    private final String title;
    private String subTitle = "";
    private String imgURL = "";
    private String url = "";
    private double fullPrice = 0D;
    private int discountPercentage = 0;
    private double discountPrice = 0D;

    public Product(String title, String price) {
        this.title = title;
        priceInitializer(price);
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getUrl() {
        return url;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setUrl(String url) {
        this.url = url;
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
                "\n\timgURL='" + imgURL + '\'' +
                "\n\turl='" + url + '\'' +
                "\nfullPrice=" + fullPrice +
                ", discountPercentage=" + discountPercentage +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
