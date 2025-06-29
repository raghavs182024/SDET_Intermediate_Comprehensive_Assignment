package trip.make;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RoundTripMakeMyTrip {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
		/*
		 * ChromeOptions chromeOptions = new ChromeOptions();
		 * WebDriverManager.chromedriver().setup(); driver = new
		 * ChromeDriver(chromeOptions);
		 */
         
         driver = new ChromeDriver();

//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testRoundTrip() throws InterruptedException {
        driver.get("https://www.makemytrip.com/");

        //Skip login pop-up window
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='commonModal__close']"))).click();

        driver.findElement(By.xpath("//li[@data-cy='roundTrip']")).click();
       Thread.sleep(5000);

        driver.findElement(By.id("fromCity")).click();
        driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("Hyderabad");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'Hyderabad')]"))).click();
        driver.findElement(By.id("toCity")).click();
        driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("Chennai");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'Chennai')]"))).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//div[@aria-label='Thu Jul 17 2025']")).click();
        driver.findElement(By.xpath("//div[@aria-label='Sun Jul 20 2025']")).click();
        driver.findElement(By.xpath("//a[text()='Search']")).click();
        Thread.sleep(8000);

        try{
            Assert.assertEquals(driver.findElement(By.xpath("//*[contains(text(),'Hyderabad → Chennai')]")).getText(), "Hyderabad → Chennai");
        }catch (Exception e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }

    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}