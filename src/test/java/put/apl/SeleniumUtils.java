package put.apl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SeleniumUtils {

    public WebDriver driver;
    public WebDriverWait normalWait;
    public WebDriverWait experimentWait;
    public String DRIVER_LOCATION = "chromedriver.exe"; // on Windows
    //String DRIVER_LOCATION = "/usr/local/bin/chromedriver";

    public final int SUPPORTED_VERTEX = 500;

    void clickWithName(String name){
        normalWait.until(presenceOfElementLocated(By.xpath("//*[text()='" + name + "']"))).click();
    }

    void clickWithId(String id){
        normalWait.until(presenceOfElementLocated(By.id(id))).click();
    }

    SeleniumUtils(int port){
        System.setProperty("webdriver.chrome.driver", DRIVER_LOCATION);
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        //chromeOptions.addArguments("--no-sandbox");
        //chromeOptions.addArguments("--disable-extensions");
        //chromeOptions.addArguments("--disable-dev-shm-usage");
        //chromeOptions.addArguments("--disable-gpu");
        driver = new ChromeDriver(chromeOptions);
        normalWait = new WebDriverWait(driver, Duration.ofSeconds(3).getSeconds());
        experimentWait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        driver.get("http://localhost:" + port);
        try{
            clickWithName("Allow Cookies");
        }catch (Exception ignored){}
    }

    void testGraphAlgorithm(String a, String g, String w,  Integer numberOfVertices, Integer density) {
        driver.manage().deleteAllCookies();
        try {
            clickWithName("GRAPH ALGORITHMS");
            setNewGraphAlgorithm(a, g, w, numberOfVertices, density);
            normalWait.until(presenceOfElementLocated(By.xpath("//*[text()='Submit']"))).click();
            experimentWait.until(presenceOfElementLocated(By.id("opt_0")));
        }catch (Exception e){
            System.out.println("Failed " + a + ":" + g + ":" + w + ":" +
                    numberOfVertices.toString() + ":" + density.toString());
            System.out.println(e);
            fail();
        }
    }

    void setNewGraphAlgorithm(String algorithm, String generator, String representation, Integer numberOfVertices, Integer density){
        clickWithName("Add Experiment");
        clickWithId("algorithm");
        clickWithId("algorithm_"+algorithm);
        clickWithId("generator");
        clickWithId("generator_"+generator);
        clickWithId("representation");
        clickWithId("representation_"+ representation);
        WebElement inputVertices =  normalWait.until(presenceOfElementLocated(By.id("numberOfVertices")));
        inputVertices.click();
        inputVertices.clear();
        inputVertices.sendKeys(numberOfVertices.toString());
        WebElement inputDensity =  normalWait.until(presenceOfElementLocated(By.id("density")));
        inputDensity.click();
        inputDensity.clear();
        inputDensity.sendKeys(density.toString());
    }

    void setNewSortingAlgorithm(String algorithm, String dataDistribution, Integer maxValue){
        clickWithName("Add Experiment");
        clickWithId("algorithm");
        clickWithId("algorithm_"+algorithm);
        clickWithId("data_distribution");
        clickWithId("data_distribution_"+dataDistribution);
        WebElement input =  normalWait.until(presenceOfElementLocated(By.id("max_value")));
        input.click();
        input.clear();
        input.sendKeys(maxValue.toString());
    }
}
