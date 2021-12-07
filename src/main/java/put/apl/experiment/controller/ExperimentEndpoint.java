package put.apl.experiment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import put.apl.experiment.dto.ExperimentsResults;
import put.apl.experiment.dto.GraphExperiment;
import put.apl.experiment.dto.SortingExperiment;
import put.apl.experiment.service.GraphService;
import put.apl.experiment.service.SchedulerService;
import put.apl.experiment.service.SortingService;

import java.util.List;

@RestController
@RequestMapping("/api/experiment")
@AllArgsConstructor
public class ExperimentEndpoint {

    SchedulerService schedulerService;
    SortingService sortingService;
    GraphService graphService;

    @PostMapping("/sorting")
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
    @GetMapping("/sorting/algorithms")
    public String[] getPossibleSortingAlgorithms(){
        return sortingService.getPossibleSortingAlgorithms();
    }

    @GetMapping("/sorting/dataDistributions")
    public String[] getPossibleSortingDataDistributions(){
        return sortingService.getPossibleDataDistributions();
    }

    @PostMapping("/graph")
    public ResponseEntity<String> startGraphExperiments(
            @RequestBody List<GraphExperiment> experiments,
            @RequestParam(name = "finite", defaultValue = "true") boolean finite
    ){
        String id = schedulerService.scheduleGraph(experiments, finite);
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

    @GetMapping("/possibleGraphAlgorithms")
    public String[] getPossibleGraphAlgorithms() { return graphService.getPossibleGraphAlgorithms(); }

    @GetMapping("/possibleGraphGenerators")
    public String[] getPossibleGraphGenerators() { return graphService.getPossibleGraphGenerators(); }

    @GetMapping("/possibleGraphRepresentations")
    public String[] getPossibleGraphRepresentations() { return graphService.getPossibleGraphRepresentations(); }
}
