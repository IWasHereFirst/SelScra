package com.example.selscra.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

// Singleton class
public class Setup {

    private RemoteWebDriver driver;
    private static Setup setup = null;

    private Setup() {
    }

    public static Setup getInstance(){
        if (setup == null){
            setup = new Setup();
        }
        return setup;
    }

    public RemoteWebDriver getDriver(boolean local) {

        if (local) {
            WebDriverManager.chromedriver().setup();
            this.driver = new ChromeDriver();
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            try{
                this.driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
        }

        return this.driver;
    }

    public static void loadWait(WebDriver wd, int time, String tag, String att, String attVal) {

        new WebDriverWait(wd, Duration.ofSeconds(time)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
            tag + "[" + att + "=" + attVal + "]"
        )));

    }

    public static void clickWait(WebDriver wd, int time, String tag, String att, String attVal) {

        new WebDriverWait(wd, Duration.ofSeconds(time)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                tag + "[" + att + "=" + attVal + "]"
        )));

    }

    public static void implicitW(WebDriver wd, int time) {
        wd.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

}
