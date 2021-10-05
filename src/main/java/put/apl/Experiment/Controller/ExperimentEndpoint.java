package put.apl.Experiment.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.apl.Experiment.Dto.ExperimentsResults;
import put.apl.Experiment.Dto.GraphExperiment;
import put.apl.Experiment.Dto.SortingExperiment;
import put.apl.Experiment.Service.SchedulerService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/apl-api/experiment")
@AllArgsConstructor
public class ExperimentEndpoint {

    SchedulerService schedulerService;

    @PostMapping("/sort")
    public ResponseEntity<String> startSortingExperiments(
            @RequestBody List<SortingExperiment> experiments,
            @RequestParam(name = "finite", defaultValue = "true") boolean finite
    ){
        String id = schedulerService.scheduleSoritng(experiments, finite);
        if(id == null){
            return ResponseEntity.badRequest()
                    .body(null);
        }else{
            return ResponseEntity.ok()
                    .body(id);
        }
    }

    @PostMapping("/graph")
    public ResponseEntity<String> startGraphExperiments(
            @RequestBody List<GraphExperiment> experiments,
            @RequestParam(name = "finite", defaultValue = "true") boolean finite
    ){
        String id = schedulerService.scheduleGraph(experiments, true);
        if(id == null){
            return ResponseEntity.badRequest()
                    .body(null);
        }else{
            return ResponseEntity.ok()
                    .body(id);
        }
    }

    @GetMapping("/{id}")
    public ExperimentsResults getExperimentsResults(@PathVariable String id) {
        ExperimentsResults res = schedulerService.getExperimentsResults(id);
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteExperiments(@PathVariable String id){
        schedulerService.deleteExperiments(id);
    }
}
