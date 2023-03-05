package com.example.selscra.lidl;

import com.example.selscra.common.Setup;
import com.example.selscra.dto_lidl.DiscountProduct;
import com.example.selscra.dto_lidl.WishlistProduct;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.*;
import static com.example.selscra.common.Setup.clickWait;

public class Lidl {

    private static final String URL_LOGIN_WEBSITE = "https://www.lidl.sk/user-api/login?step=login&redirect=https%3A%2F%2Fwww.lidl.sk%2F";
    private static final String URL_WISHLIST = "https://www.lidl.sk/wishlist/";
    private static final String URL_HOME = "https://www.lidl.sk";
    private static final String LOGIN_EMAIL = "umpiklumpik@gmail.com";
    private static final String LOGIN_PASSWORD = "Arcadosi01";

    //private RemoteWebDriver driver;
    private Setup setup;

    private final RemoteWebDriver driver;

    public Lidl(boolean local) {
        this.driver = Setup.getInstance().getDriver(local);
        // Set proper working dimensions
        driver.manage().window().setSize(new Dimension(1280, 1024));
    }

    public Lidl login() {

        driver.get(URL_LOGIN_WEBSITE);
        fillLoginForm();
        return this;
    }

    private void fillLoginForm() {

        // Login email
        clickWait(driver, 5, "input", "name", "EmailOrPhone");
        driver.findElement(By.cssSelector("input[name=EmailOrPhone]")).sendKeys(LOGIN_EMAIL);
        driver.findElement(By.cssSelector("button[id=button_btn_submit_email]")).click();
        // Login password
        clickWait(driver, 5, "input", "name", "Password");
        driver.findElement(By.cssSelector("input[name=Password]")).sendKeys(LOGIN_PASSWORD);
        driver.findElement(By.cssSelector("button[id=button_submit]")).click();
    }

    public Lidl acceptCookies() {

        clickWait(driver, 5, "button", "class", "cookie-alert-extended-button");
        driver.findElement(By.cssSelector("button.cookie-alert-extended-button")).click();
        return this;
    }

    public List<WishlistProduct> getAllWishlistProducts() throws NoSuchElementException, TimeoutException {

        List<WishlistProduct> wishlistProductList = new ArrayList<>();
        try {
            driver.get(URL_WISHLIST);
            //loadWait(driver, "ul", "class", "wishlist-items-list");
            List<WebElement> wishlistItems = driver.findElements(By.cssSelector("li.wishlist-items-list__item")); // nazov wapper divu

            wishlistItems.forEach(item -> {
                String title = item.findElement(By.cssSelector(".wishlist-item__product-headline-content")).getText();
                String price = item.findElement(By.cssSelector(".wishlist-item__price")).getText().replaceAll("[^[0-9].\\n]", "");
                String subTitle = item.findElement(By.cssSelector(".wishlist-item__product-headline-description")).getText();
                String imageUrl = item.findElement(By.cssSelector(".wishlist-item__product-image img")).getAttribute("src");
                String url = item.findElement(By.cssSelector(".wishlist-item__product-headline-content a")).getAttribute("href");
                WishlistProduct wishlistProduct = new WishlistProduct(title, price);
                String s1 = url;
                s1 = s1.substring(22);
                int index = s1.indexOf("/");
                s1 = s1.substring(index+2);
                wishlistProduct.setId(Long.parseLong(s1));
                wishlistProduct.setUrl(url);
                wishlistProduct.setSubTitle(subTitle);
                wishlistProduct.setImgUrl(imageUrl);
                wishlistProductList.add(wishlistProduct);
            });
        } catch (NoSuchElementException | TimeoutException e) { // Both Exceptions occur when wishlist is down
            System.out.println(e.getMessage().indexOf("wishlist-items-list") > 0 ? "Website currently unavailable" : e.getMessage());
        } finally{
            driver.quit();
        }
        return wishlistProductList;
    }

    public List<DiscountProduct> getCurrentDiscounts() {
        List<DiscountProduct> productList = new ArrayList<>();
        try{
            driver.get(URL_HOME);
            acceptCookies();
            List<MainMenu> menu = getDiscountNavMenuAndLinks();

            menu.forEach(m -> {
                m.getSubmenu().forEach(s -> {
                    System.out.println(m.getMenuTitle() +
                            "\n\t" + s.getHeadline() +
                            "\n\t" + s.getAvailableFrom() +
                            "\n\t" + s.getLink());
                    List<DiscountProduct> pro = getDiscountItems(s, s.getLink());
                    productList.addAll(pro);
                });
            });
        } catch (Exception e){
            System.out.println("Error occured");
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return productList;
    }

    private List<MainMenu> getDiscountNavMenuAndLinks() {


        driver.findElement(By.cssSelector("ol[class*='n-header__main-navigation'] li:nth-of-type(2)")).click();
        WebElement websiteTop = driver.findElement(By.cssSelector("#__layout > div > div.ATheCampaign__Wrapper > main > div > section:nth-child(1)"));
        List<MainMenu> mainMenuList = new ArrayList<>();

        for (int i = 1; i < 4; i++) {

            // Click on top menu (3 times) [online shop, tento tyzden, buduci tyzden]
            driver.findElement(By.cssSelector("li[id^='ATheHeroStage__Tab']:nth-of-type(" + i + ")")).click();
            List<WebElement> menuElements = websiteTop.findElements(By.cssSelector("div > div > section:nth-of-type(" + i + ")"));

            menuElements.forEach(mainMenu -> {

                String menuTitle = mainMenu.findElement(By.cssSelector("span[class='ATheHeroStage__AccordionTabHeading']")).getText();
                MainMenu menuObject = new MainMenu(menuTitle);
                List<WebElement> subMenu = mainMenu.findElements(By.cssSelector("div.ATheHeroStage__Offer"));

                subMenu.forEach(subMenuItems -> {

                    String headline = subMenuItems.findElement(By.cssSelector(".ATheHeroStage__Headline")).getText();
                    String availableFrom = subMenuItems.findElement(By.cssSelector(".ATheHeroStage__OfferHeadlineText")).getText();
                    String link = subMenuItems.findElement(By.cssSelector("a")).getAttribute("href");

                    SubMenu submenuObject = new SubMenu(headline, availableFrom, link);
                    menuObject.addSubmenu(submenuObject);

                });
                mainMenuList.add(menuObject);
            });
        }
        //loadWait(driver, "div", "class", "n-header__flyout-wrapper");
        return mainMenuList;
    }

    public List<DiscountProduct> getDiscountItems(SubMenu subMenu, String url){
        List<DiscountProduct> productList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();

            Elements prodList = doc.select(".ATheCampaign__Page section[data-selector='GRID'] li[class$='ACampaignGrid__item--product']");
            prodList.forEach(element -> {
                String product = element.select("div[data-grid-label='grid-fragment']").attr("data-grid-data");
                product = product.substring(1, product.length()-1);
                Gson gson = new Gson();
                DiscountProduct discountProduct = gson.fromJson(product, DiscountProduct.class);
                DiscountProduct prod = discountProduct.initializeProduct();
                subMenu.addProductToSubmenu(prod);
                productList.add(prod);
                System.out.println(discountProduct.toString());
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    private String priceLabeler(String s){
        return s.charAt(s.length() - 1) == '%' ? s : "0";
    }
}
