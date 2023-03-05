package com.example.selscra;

import com.example.selscra.common.Checkers;
import com.example.selscra.dto_lidl.DiscountProduct;
import com.example.selscra.lidl.Lidl;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.IOException;


@ServletComponentScan
@SpringBootApplication
public class SelScraApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelScraApplication.class, args);

        //Lidl lidl = new Lidl(true);
        //lidl.login().acceptCookies().getAllWishlistProducts();

        //lidl.getCurrentDiscounts();
        Checkers.grid("https://www.lidl.sk/c/sportova-vybava/a10008515");


    }
}
