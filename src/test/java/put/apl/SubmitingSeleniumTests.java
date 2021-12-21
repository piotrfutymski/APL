package put.apl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubmitingSeleniumTests {

    SeleniumUtils utils;

    @LocalServerPort
    private int port;

    @BeforeAll
    void initAll() {
        utils = new SeleniumUtils(port);
    }

    @Test
    void submitSortingAlgorithms(){
        try{
            utils.clickWithName("SORTING ALGORITHMS");
            utils.setNewSortingAlgorithm("Binary Insertion Sort", "Random Data", 50);
            utils.setNewSortingAlgorithm("Merge Sort", "Random Data", 50);
            utils.clickWithName("Submit");
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void submitGraphAlgorithms(){
        try{
            utils.clickWithName("GRAPH ALGORITHMS");
            utils.setNewGraphAlgorithm("Dijkstra Algorithm",
                    "Euler Directed Graph Generator",
                    "List Of Edges Directed",
                    50,80);
            utils.setNewGraphAlgorithm("Dijkstra Algorithm",
                    "Euler Directed Graph Generator",
                    "Weighted Adjacency Matrix Directed",
                    50,80);
            utils.normalWait.until(presenceOfElementLocated(By.xpath("//*[text()='Submit']"))).click();
        }catch (Exception e){
            fail();
        }
    }
}
