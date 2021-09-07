package put.apl.Experiment;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apl-api/experiment")
public class ExperimentEndpoint {


   class Test{
    public int x,y;
    public Test(int x, int y){
        this.x = x;
        this.y = y;
    }
   }

    @GetMapping
    public Test Hello(){
        return new Test(1,5);
    }
}
