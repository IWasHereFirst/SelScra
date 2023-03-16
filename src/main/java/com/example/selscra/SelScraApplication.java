package com.example.selscra;

import com.example.selscra.common.Checkers;
import com.example.selscra.lidl.Lidl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SelScraApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelScraApplication.class, args);

        //Lidl.addProductFromUrl("https://www.lidl.sk/p/parkside-aku-striekacia-pistol-na-farbu-pfsa-20-li-a1-bez-akumulatora/p100351822");
        //lidl.login().acceptCookies().getAllWishlistProducts();

        //lidl.getCurrentDiscounts();
        Checkers.grid("https://www.lidl.sk/c/sportova-vybava/a10008515");


    }
}
