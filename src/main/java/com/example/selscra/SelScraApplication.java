package com.example.selscra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SelScraApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelScraApplication.class, args);

        //Lidl lidl = new Lidl(true);
        //lidl.login().acceptCookies().getAllWishlistProducts();

        //lidl.getCurrentDiscounts();
        //Checkers.grid("https://www.lidl.sk/c/sportova-vybava/a10008515");


    }
}
