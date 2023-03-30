package com.example.selscra.lidl;

import com.example.selscra.common.Setup;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.dto_lidl.Product;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.selscra.common.Setup.clickWait;

public class Lidl {

    private static final String URL_HOME = "https://www.lidl.sk";

    //private RemoteWebDriver driver;
    private Setup setup;

    private final RemoteWebDriver driver;

    public Lidl(boolean local) {
        this.driver = Setup.getInstance().getDriver(local);
        // Set proper browser window working dimensions
        driver.manage().window().setSize(new Dimension(1280, 1024));
    }

    public Lidl acceptCookies() {
        clickWait(driver, 5, "button", "class", "cookie-alert-extended-button");
        driver.findElement(By.cssSelector("button.cookie-alert-extended-button")).click();
        return this;
    }

    public ConcurrentHashMap<Category, Integer> getCurrentDiscounts() {
        ConcurrentHashMap<Category, Integer> menu = getDiscountNavMenuAndLinks();
        menu.forEach((m, n) -> {
            getAllDiscountProducts(m, m.getUrl());
        });

        return menu;
    }

    public ConcurrentHashMap<Category, Integer> getDiscountNavMenuAndLinks() {

        ConcurrentHashMap<Category, Integer> categoryList = new ConcurrentHashMap<>();
        try{
            driver.get(URL_HOME);
            acceptCookies();

            driver.findElement(By.cssSelector("ol[class*='n-header__main-navigation'] li:nth-of-type(2)")).click();
            WebElement websiteTop = driver.findElement(By.cssSelector("#__layout > div > div.ATheCampaign__Wrapper > main > div > section:nth-child(1)"));
            for (int i = 1; i <= 3; i++) {

                // Click on top menu (3 times) [online shop, tento tyzden, buduci tyzden]
                driver.findElement(By.cssSelector("li[id^='ATheHeroStage__Tab']:nth-of-type(" + i + ")")).click();
                List<WebElement> menuElements = websiteTop.findElements(By.cssSelector("div > div > section:nth-of-type(" + i + ")"));

                menuElements.forEach(mainMenu -> {

                    List<WebElement> subMenu = mainMenu.findElements(By.cssSelector("div.ATheHeroStage__Offer"));

                    subMenu.forEach(subMenuItems -> {

                        // subMenu = Category
                        String url = subMenuItems.findElement(By.cssSelector("a")).getAttribute("href");
                        if(url.indexOf("online-magazin") > 0) return;
                        long categoryId = Long.parseLong(url.substring(ordinalIndexOf(url, "/", 4)+2, url.indexOf("?")));
                        String name = subMenuItems.findElement(By.cssSelector(".ATheHeroStage__Headline")).getText();
                        String availableFrom = subMenuItems.findElement(By.cssSelector(".ATheHeroStage__OfferHeadlineText")).getText();

                        Category category = new Category();
                        category.setId(categoryId);
                        category.setName(name);
                        category.setAvailableFrom(availableFrom);
                        category.setUrl(url);
                        categoryList.put(category, 0);
                    });
                });
            }
        } finally {
            driver.quit();
        }
        return categoryList;
    }

    public void getAllDiscountProducts(Category category, String url){
        try {
            Document doc = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();

            Elements prodList = doc.select(".ATheCampaign__Page section[data-selector='GRID'] li[class$='ACampaignGrid__item--product']");
            prodList.forEach(element -> {
                String productJson = element.select("div[data-grid-label='grid-fragment']").attr("data-grid-data");
                productJson = productJson.substring(1, productJson.length()-1);
                Gson gson = new Gson();
                Product product = gson.fromJson(productJson, Product.class);
                product.initializeProduct();
                product.setCatId(category.getId());
                category.addProductToSubmenu(product);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Category addProductFromUrl(String pageUrl){
        Product product = new Product();
        Category category = new Category();
        category.addProductToSubmenu(product);
        urlProduct(category, product, pageUrl);
        return category;
    }

    public static void urlProduct(Category cat, Product prod, String url) {
        try{
            Document doc = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();

            long catId;
            String catUrl = "";
            String catName = "";

            catUrl = "https://www.lidl.sk" + doc.select("ol[class$='m-breadcrumbs--full'] li:last-of-type a").attr("href");
            catName = doc.select("ol[class$='m-breadcrumbs--full'] li:last-of-type a").text();
            catId = Long.parseLong(catUrl.substring(ordinalIndexOf(catUrl, "/", 4) + 2));

            cat.setId(catId);
            cat.setName(catName);
            cat.setUrl(catUrl);

            long id = 0;
            String title = "";
            String subTitle = "";
            String imgUrl = "";
            String price = "";

            String actualPrice = doc.select(".m-price__bottom").text().replaceAll("[^[0-9].]", "");
            price = actualPrice.equals("") ? "0.0" : (actualPrice); // actual price
            String label = doc.select(".m-price__label").text();
            price += "\n" + priceLabeler(label).replaceAll("[^[0-9].]", ""); // label, price labeler cut's out other than discounts (grams etc.)
            String oldPrice = doc.select(".m-price__top").text().replaceAll("[^[0-9].]", "");
            price += "\n" + (oldPrice.equals("") ? "0.0" : oldPrice); // old price
            id = Long.parseLong(doc.select(".buybox__erp-number").text());
            title = doc.select(".keyfacts__title").text();
            imgUrl = doc.select(".gallery-image__img").attr("src");
            subTitle = doc.select("div[class$='info__content--description']").html();

            prod.setErpNumber(id);
            prod.setFullTitle(title);
            prod.setImage(imgUrl);
            prod.setCanonicalUrl(url);
            prod.setSubTitle(subTitle);
            prod.priceInitializer(price);
            prod.setCatId(catId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String priceLabeler(String s){
            return s.contains("%") ? s : "0";
    }

    public static int ordinalIndexOf(String str, String subStr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(subStr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    /*
    public List<Category> getCurrentDiscounts() {
        List<Category> menu = getDiscountNavMenuAndLinks();
        menu.forEach(m -> {
            getAllDiscountProducts(m, m.getUrl());
        });

        return menu;
    }

     */

    /*
    public List<Category> getDiscountNavMenuAndLinks() {

        List<Category> categoryList = new ArrayList<>();
        try{
            driver.get(URL_HOME);
            acceptCookies();

            driver.findElement(By.cssSelector("ol[class*='n-header__main-navigation'] li:nth-of-type(2)")).click();
            WebElement websiteTop = driver.findElement(By.cssSelector("#__layout > div > div.ATheCampaign__Wrapper > main > div > section:nth-child(1)"));
            for (int i = 1; i <= 3; i++) {

                // Click on top menu (3 times) [online shop, tento tyzden, buduci tyzden]
                driver.findElement(By.cssSelector("li[id^='ATheHeroStage__Tab']:nth-of-type(" + i + ")")).click();
                List<WebElement> menuElements = websiteTop.findElements(By.cssSelector("div > div > section:nth-of-type(" + i + ")"));

                menuElements.forEach(mainMenu -> {

                    List<WebElement> subMenu = mainMenu.findElements(By.cssSelector("div.ATheHeroStage__Offer"));

                    subMenu.forEach(subMenuItems -> {

                        // subMenu = Category
                        String url = subMenuItems.findElement(By.cssSelector("a")).getAttribute("href");
                        if(url.indexOf("online-magazin") > 0) return;
                        long categoryId = Long.parseLong(url.substring(ordinalIndexOf(url, "/", 4)+2, url.indexOf("?")));
                        String name = subMenuItems.findElement(By.cssSelector(".ATheHeroStage__Headline")).getText();
                        String availableFrom = subMenuItems.findElement(By.cssSelector(".ATheHeroStage__OfferHeadlineText")).getText();

                        Category category = new Category();
                        category.setId(categoryId);
                        category.setName(name);
                        category.setAvailableFrom(availableFrom);
                        category.setUrl(url);
                        categoryList.add(category);
                    });
                });
            }
        } finally {
            driver.quit();
        }
        return categoryList;
    }
     */
}
