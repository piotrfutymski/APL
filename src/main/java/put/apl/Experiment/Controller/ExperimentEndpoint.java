package put.apl.Experiment.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import put.apl.Experiment.Dto.SortingExperiment;

import java.util.List;

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

    @PostMapping
    public String startExperiments(@RequestBody List<SortingExperiment> experiments){
       return "You want to make " + String.valueOf(experiments.size()) + " experiments";
    }
}
