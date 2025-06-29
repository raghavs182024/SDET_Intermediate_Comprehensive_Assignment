package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class RoundTripSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I open the MakeMyTrip website")
    public void openWebsite() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("I skip the login pop-up")
    public void skipLoginPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='commonModal__close']"))).click();
    }

    @When("I select round trip option")
    public void selectRoundTrip() {
        driver.findElement(By.xpath("//li[@data-cy='roundTrip']")).click();
    }

    @When("I enter {string} as the source city")
    public void enterSourceCity(String sourceCity) {
        driver.findElement(By.id("fromCity")).click();
        driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys(sourceCity);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + sourceCity + "')]"))).click();
    }

    @When("I enter {string} as the destination city")
    public void enterDestinationCity(String destinationCity) {
        driver.findElement(By.id("toCity")).click();
        driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys(destinationCity);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + destinationCity + "')]"))).click();
    }

    @When("I select departure date {string}")
    public void selectDepartureDate(String departureDate) {
        driver.findElement(By.xpath("//div[@aria-label='" + departureDate + "']")).click();
    }

    @When("I select return date {string}")
    public void selectReturnDate(String returnDate) {
        driver.findElement(By.xpath("//div[@aria-label='" + returnDate + "']")).click();
    }

    @When("I click on search flights")
    public void clickSearchFlights() {
        driver.findElement(By.xpath("//a[text()='Search']")).click();
    }

    @Then("I should see {string} in the search results")
    public void verifySearchResults(String expectedText) {
        try {
            Assert.assertEquals(driver.findElement(By.xpath("//*[contains(text(),'" + expectedText + "')]")).getText(), expectedText);
        } catch (Exception e) {
            System.out.println("Assertion failed: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}