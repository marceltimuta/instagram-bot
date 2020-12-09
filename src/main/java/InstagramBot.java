import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InstagramBot {

    private final WebDriver driver;

    private final String BASE_URL = "https://www.instagram.com/";
    private final String LIKE_METHOD = "Like";

    private By acceptCookiesButton = By.xpath("//button[normalize-space()='Accept']");
    private By usernameField = By.cssSelector("input[name='username']");
    private By passwordField = By.xpath("//input[contains(@name,'password')]");
    private By signInButton = By.xpath("//div[contains(text(),'Log In')]");
    private By photo = By.cssSelector("div.v1Nh3 a");
    private By likeButton = By.xpath("//div[@class='QBdPU ']//span//*[local-name()='svg']");

    public InstagramBot(WebDriver driver) {
        this.driver = driver;
        driver.get(BASE_URL);
    }

    public void login(String userName, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,3000);

        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
        driver.findElement(usernameField).sendKeys(userName);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(signInButton).click();
        Thread.sleep(2000);

    }

    public void navigateToUser(String account) {
        driver.get(BASE_URL + account);
    }

    private Set<String> getAllPhotoURL() {
        Set<String> photoURLs = new HashSet<>();
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        try {
            long lastHeight = (long) jse.executeScript("return document.body.scrollHeight");

            while (true) {
                jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(500);
                photoURLs.addAll(driver.findElements(photo).stream().map(element -> element.getAttribute("href")).collect(Collectors.toSet()));

                long newHeight = (long) jse.executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return photoURLs;
    }

    public void applyLikes() throws InterruptedException {
        Random random = new Random();
        WebDriverWait wait = new WebDriverWait(driver,3000);

        for (String photoURL:getAllPhotoURL()) {
            Thread.sleep(1500 + random.nextInt(1000));
            driver.navigate().to(photoURL);
            WebElement likeIcon = wait.until(ExpectedConditions.visibilityOf(driver.findElement(likeButton)));
            if (LIKE_METHOD.equals(likeIcon.getAttribute("aria-label"))) {
                likeIcon.click();
            }
        }
    }

    public void stopBot() {
        driver.quit();
    }
}
