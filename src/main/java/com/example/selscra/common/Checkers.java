package com.example.selscra.common;

import com.example.selscra.dto_lidl.DiscountProduct;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Checkers {

    public static void grid(String url){
        try{
            Document doc = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();

            Elements prodList = doc.select(".ATheCampaign__Page section[data-selector='GRID'] li[class$='ACampaignGrid__item--product']");
            prodList.forEach(element -> {
                String product = element.select("div[data-grid-label='grid-fragment']").attr("data-grid-data");
                product = product.substring(1, product.length() - 1);
                System.out.println(product);
                Gson gson = new Gson();
                DiscountProduct discountProduct = gson.fromJson(product, DiscountProduct.class);
                DiscountProduct prod = discountProduct.initializeProduct();
                System.out.println(discountProduct.toString());
            });
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
