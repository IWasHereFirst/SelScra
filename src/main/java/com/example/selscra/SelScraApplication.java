package com.example.selscra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.List;

@ServletComponentScan
@SpringBootApplication
public class SelScraApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelScraApplication.class, args);

        //Lidl.addProductFromUrl("https://www.lidl.sk/p/parkside-aku-striekacia-pistol-na-farbu-pfsa-20-li-a1-bez-akumulatora/p100351822");
        //lidl.login().acceptCookies().getAllWishlistProducts();

        //lidl.getCurrentDiscounts();
        //Checkers.grid("https://www.lidl.sk/c/sportova-vybava/a10008515");

        //Lidl lidl = new Lidl(true);
        //lidl.getCurrentDiscounts();
        //productList.forEach(System.out::println);

    }
}
