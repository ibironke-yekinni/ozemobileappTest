import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.annotation.Order;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class AndroidLoginTest {

    AndroidDriver driver;
    WebDriverWait wait;

    @Before //set up the driver using desired capabilities
    public void setUp() throws MalformedURLException, URISyntaxException {
        URL appiumUrl = new URL ("http://127.0.0.1:4723/wd/hub");
        URL resource = getClass().getClassLoader().getResource("apps/TheApp-v1.10.0.apk");
        File app = Paths.get(resource.toURI()).toFile();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("deviceName", "PIXEL 4 XL");
        caps.setCapability ("appPackage", "io.cloudgrey.the_app");
        caps.setCapability ("appActivity", "io.cloudgrey.the_app.MainActivity");
        caps.setCapability("app", app);
        driver = new AndroidDriver (appiumUrl, caps);

        // Just so that I know it is working fine
        System.out.println ("Application Started....");

        wait = new WebDriverWait (driver,30);
    }

    private WebElement safeFind(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Test // our actual test logic
    public void test1() {
        safeFind(MobileBy.AccessibilityId ("Login Screen"))
                .click();
        safeFind (MobileBy.AccessibilityId ("username"))
                .sendKeys("alice");
        safeFind(MobileBy.AccessibilityId ("password"))
                .sendKeys("mypassword");
        safeFind (By.xpath ("//android.view.ViewGroup[@content-desc=\"loginBtn\"]/android.widget.TextView"))
                .click();
        safeFind (By.xpath ("\t\n" +
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.TextView[2]"));
        System.out.println ("Succesfully Logged in");
        safeFind (By.xpath ("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView"))
                .click ();
        System.out.println ("Successfully Logged out");
    }

    @Test // our actual test logic
    public void test2() {
        safeFind(MobileBy.AccessibilityId ("Login Screen"))
                .click();
        safeFind (MobileBy.AccessibilityId ("username"))
                .sendKeys("wrongUsername");
        safeFind(MobileBy.AccessibilityId ("password"))
                .sendKeys("mypassword");
        safeFind (By.xpath ("//android.view.ViewGroup[@content-desc=\"loginBtn\"]/android.widget.TextView"))
                .click();
        Alert alert = driver.switchTo ().alert ();
        String alertMessage = driver.switchTo().alert().getText();
        System.out.println (alertMessage);
        alert.accept ();
    }

    @Test()
    public void test3() {
        safeFind(MobileBy.AccessibilityId ("Login Screen"))
                .click();
        safeFind (MobileBy.AccessibilityId ("username"))
                .sendKeys("alice");
        safeFind(MobileBy.AccessibilityId ("password"))
                .sendKeys("wrongPassword");
        safeFind (By.xpath ("//android.view.ViewGroup[@content-desc=\"loginBtn\"]/android.widget.TextView"))
                .click();
        Alert alert = driver.switchTo ().alert ();
        String alertMessage = driver.switchTo().alert().getText();
        System.out.println (alertMessage);
        alert.accept ();
    }

    @Test()
    public void test4() {
        safeFind(MobileBy.AccessibilityId ("Photo Demo"))
                .click();
        safeFind (By.xpath ("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[4]/android.widget.ImageView"))
                .click ();
        Alert alert = driver.switchTo ().alert ();
        String alertMessage = driver.switchTo().alert().getText();
        System.out.println (alertMessage);
        alert.accept ();
    }

    @After //clean up the session after each test
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ignore) {}
    }

}
