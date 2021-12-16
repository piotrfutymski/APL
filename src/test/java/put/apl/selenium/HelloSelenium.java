package put.apl.selenium;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.algorithms.sorting.data.SortingData;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HelloSelenium {

    WebDriver driver;
    WebDriverWait normalWait;
    WebDriverWait experimentWait;

    @BeforeAll
    void initAll() {
        System.setProperty("webdriver.edge.driver","msedgedriver");
        driver = new EdgeDriver();
        normalWait = new WebDriverWait(driver, Duration.ofSeconds(3).getSeconds());
        experimentWait = new WebDriverWait(driver, Duration.ofSeconds(40).getSeconds());
    }
    @BeforeEach
    void initEach(){
        driver.get("http://localhost:8080");
        driver.findElement(By.xpath("//*[text()='SORTING ALGORITHMS']")).click();
    }

    void setNewAlgorithm(String algorithm, String dataDistribution, Integer maxValue){
        normalWait.until(presenceOfElementLocated(By.xpath("//*[text()='Add Experiment']"))).click();
        normalWait.until(presenceOfElementLocated(By.id("algorithm"))).click();
        normalWait.until(presenceOfElementLocated(By.id("algorithm_"+algorithm))).click();
        normalWait.until(presenceOfElementLocated(By.id("data_distribution"))).click();
        normalWait.until(presenceOfElementLocated(By.id("data_distribution_"+dataDistribution))).click();
        WebElement input =  normalWait.until(presenceOfElementLocated(By.id("max_value")));
        input.click();
        input.clear();
        input.sendKeys(maxValue.toString());
    }

    @Test
    void submitSortingAlgorithms(){
        try{
            setNewAlgorithm("Binary Insertion Sort", "Random Data", 50);
            setNewAlgorithm("Merge Sort", "Random Data", 50);
            normalWait.until(presenceOfElementLocated(By.xpath("//*[text()='Submit']"))).click();
        }catch (Exception e){
            fail();
        }
    }

}
