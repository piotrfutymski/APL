package put.apl;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import java.time.Duration;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeneratorsSeleniumTests {

    final String[] generators = {"Connected Directed Graph Generator", "Euler Directed Graph Generator", "Connected Undirected Graph Generator",
            "Euler Undirected Graph Generator", "Directed Graph Generator", "Undirected Graph Generator"};

    final String algorithm = "Breadth First Search";

    final String[] representations = {"Adjacency Matrix Directed", "Adjacency Matrix Undirected"};

    final int[] densities = {1, 10, 25, 50, 75, 90, 100};

    SeleniumUtils utils;

    @LocalServerPort
    private int port;

    @BeforeAll
    void initAll() {
        utils = new SeleniumUtils(port);
    }

    @Test
    void graphTestGenerators0() {
        for (int density : densities) {
            utils.testGraphAlgorithm(algorithm, generators[0], representations[0], utils.SUPPORTED_VERTEX, density);
        }
    }

    @Test
    void graphTestGenerators1() {
        for (int density : densities) {
            if(density > 80)
                continue;
            utils.testGraphAlgorithm(algorithm, generators[1], representations[0], utils.SUPPORTED_VERTEX, density);
        }
    }

    @Test
    void graphTestGenerators2() {
        for (int density : densities) {
            utils.testGraphAlgorithm(algorithm, generators[2], representations[1], utils.SUPPORTED_VERTEX, density);
        }
    }

    @Test
    void graphTestGenerators3() {
        for (int density : densities) {
            if(density > 80)
                continue;
            utils.testGraphAlgorithm(algorithm, generators[3], representations[1], utils.SUPPORTED_VERTEX, density);
        }
    }

    @Test
    void graphTestGenerators4() {
        for (int density : densities) {
            utils.testGraphAlgorithm(algorithm, generators[4], representations[0], utils.SUPPORTED_VERTEX, density);
        }
    }

    @Test
    void graphTestGenerators5() {
        for (int density : densities) {
            utils.testGraphAlgorithm(algorithm, generators[5], representations[1], utils.SUPPORTED_VERTEX, density);
        }
    }

}
