package com.example.selscra;

import com.example.selscra.lidl.Lidl;
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

    }

}
