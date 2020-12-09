import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties properties = ReadProperties.readPropertiesFile();

        InstagramBot instaBot = new InstagramBot(new FirefoxDriver());
        instaBot.login(properties.getProperty("username") , properties.getProperty("password"));
        instaBot.navigateToUser(properties.getProperty("account"));
        instaBot.applyLikes();
        instaBot.stopBot();

    }
}
