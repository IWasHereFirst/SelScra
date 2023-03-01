package com.example.selscra.lidl;

import com.example.selscra.common.Setup;
import com.example.selscra.dto_lidl.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import static com.example.selscra.common.Setup.clickWait;
import static com.example.selscra.common.Setup.loadWait;

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
        clickWait(driver, "input", "name", "EmailOrPhone");
        driver.findElement(By.cssSelector("input[name=EmailOrPhone]")).sendKeys(LOGIN_EMAIL);
        driver.findElement(By.cssSelector("button[id=button_btn_submit_email]")).click();
        // Login password
        clickWait(driver, "input", "name", "Password");
        driver.findElement(By.cssSelector("input[name=Password]")).sendKeys(LOGIN_PASSWORD);
        driver.findElement(By.cssSelector("button[id=button_submit]")).click();
    }

    public Lidl acceptCookies() {

        clickWait(driver, "button", "class", "cookie-alert-extended-button");
        driver.findElement(By.cssSelector("button.cookie-alert-extended-button")).click();
        return this;
    }

    public List<Product> getAllWishlistProducts() throws NoSuchElementException, TimeoutException {

        List<Product> productList = new ArrayList<>();
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
                Product product = new Product(title, price);
                product.setUrl(url);
                product.setSubTitle(subTitle);
                product.setImgUrl(imageUrl);
                productList.add(product);
            });
        } catch (NoSuchElementException | TimeoutException e) { // Both Exceptions occur when wishlist is down
            System.out.println(e.getMessage().indexOf("wishlist-items-list") > 0 ? "Website currently unavailable" : e.getMessage());
        } finally{
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

    private void getDiscountItems(SubMenu subMenu, String url){
        driver.navigate().to(url);

        WebElement discountWrappers = driver.findElement(By.cssSelector(".ATheCampaign__Page"));
        System.out.println("getDiscountItems");
        List<WebElement> sections = discountWrappers.findElements(By.cssSelector("section[class$='ATheCampaign__SectionWrapper--relative']"));
        System.out.println("size " + sections.size());
        sections.forEach(e -> {
            List<WebElement> products = e.findElements(By.cssSelector("li[class$='ACampaignGrid__item--product']"));
            System.out.println("products count " + products.size());
            products.forEach(product -> {
                System.out.println(product.findElement(By.cssSelector(".grid-box__content")).getText());
//                System.out.println(product.findElement(By.cssSelector("h2[class^='grid-box__headline'")).getText());
            });
        });



        /*discountWrappers.forEach(a -> {
            System.out.println("section");
            List<WebElement> discountItems = a.findElements(By.cssSelector("li[class$='ACampaignGrid__item--product']"));
            discountItems.forEach(b -> {
                try {
                    System.out.println(b.findElement(By.cssSelector("div[class='m-price__wrapper']")).getText());
                    System.out.println(b.findElement(By.cssSelector("h2[class^='grid-box__headline'")).getText());
                } catch (NoSuchElementException e){
                    System.out.println("Not found");
                }
            });
        });
        */

    }

    public void getCurrentDiscounts() {

        try{
            driver.get(URL_HOME);
            List<MainMenu> menu = getDiscountNavMenuAndLinks();

            menu.forEach(m -> {
                m.getSubmenu().forEach(s -> {
                    System.out.println(m.getMenuTitle() +
                            "\n\t" + s.getHeadline() +
                            "\n\t" + s.getAvailableFrom() +
                            "\n\t" + s.getLink());
                    getDiscountItems(s, s.getLink());
                    //loadWait(driver, "div", "class", "footer__wrapper");
                });
            });
        } catch (Exception e){
            System.out.println("Error occured");
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

    private String priceLabeler(String s){
        return s.charAt(s.length() - 1) == '%' ? s : "0";
    }
}
/*
private void getDiscountItems(SubMenu subMenu, String url){
        driver.navigate().to(url);

        List<WebElement> discountWrappers = driver.findElements(By.cssSelector(".ATheCampaign__Page"));
        discountWrappers.forEach(c -> {
            System.out.println("section");
            List<WebElement> discountItems = c.findElements(By.cssSelector("li[class^='ACampaignGrid__item']"));
            discountItems.forEach(a -> {
                System.out.println("item");
                String title = "";
                String subTitle = "";
                String price = "";
                String imgUrl = "";
                String prodUrl = "";
                try{
                    price = a.findElement(By.cssSelector(".m-price__bottom")).getText().replaceAll("[^[0-9].]", ""); // actual price
                } catch (NoSuchElementException e) {
                    price += "0.0";
                }
                try{
                    price += "\n" + priceLabeler(a.findElement(By.cssSelector(".m-price__label")).getText()).replaceAll("[^[0-9].]", ""); // label
                } catch (NoSuchElementException e) {
                    price += "\n0";
                }

                try{
                    price += "\n" + a.findElement(By.cssSelector(".m-price__top")).getText().replaceAll("[^[0-9].]", ""); // old price
                } catch (NoSuchElementException e) {
                    price += "\n0.0";
                }

                try{
                    title = a.findElement(By.cssSelector("h2[data-qa-label='product-grid-box-title']")).getText();
                    imgUrl = a.findElement(By.cssSelector("img")).getAttribute("src");
                    prodUrl = a.findElement(By.cssSelector("a")).getAttribute("href");
                    subTitle = a.findElement(By.cssSelector(".product-grid-box__text")).getText();

                } catch (NoSuchElementException e){
                    //System.out.println("Text wasn't found");
                }
                //System.out.println("Price str: " + price);

                Product product = new Product(title, price);
                product.setImgUrl(imgUrl);
                product.setUrl(prodUrl);
                product.setSubTitle(subTitle);
                subMenu.addProductToSubmenu(product);
                //System.out.println(product);

                //System.out.println("\t\tTitle: " + title + " subTitle: " + subTitle + "\nimgUrl: " + imgUrl + "\nprodUrl: " + prodUrl + " price: " + price);
            });
        });

    }
 */