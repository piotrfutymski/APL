package put.apl.Experiment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import put.apl.Experiment.Dto.ExperimentsResults;
import put.apl.Experiment.Dto.GraphExperiment;
import put.apl.Experiment.Dto.SortingExperiment;

import java.util.*;
import java.util.function.Function;

@Service
public class SchedulerService {

    List<AlgorithmTask> taskQueue;

    @Autowired
    SortingService sortingService;

    public SchedulerService(){
        taskQueue = new ArrayList<>();
    }

    public String scheduleSoritng(List<SortingExperiment> experiments) {
        if(experiments.size() == 0)
            return null;
        String id = UUID.randomUUID().toString();
        AlgorithmTask task =  new AlgorithmTask(id, ()->sortingService.runExperiments(id, experiments));
        taskQueue.add(task);
        return id;
    }

    public String scheduleGraph(List<GraphExperiment> experiments) {
        return null;
    }

    public ExperimentsResults getExperimentsResults(String id) {
        Integer index = findTaskPositionInQueue(id);
        if(index == 0)
            return ExperimentsResults.builder()
                    .queuePosition(0)
                    .status(ExperimentsResults.ExperimentStatus.CALCULATING)
                    .build();
        if(index > 0)
            return ExperimentsResults.builder()
                    .queuePosition(index)
                    .status(ExperimentsResults.ExperimentStatus.QUEUED)
                    .build();

        ExperimentsResults res = ExperimentsResults.builder()
                .queuePosition(null)
                .status(ExperimentsResults.ExperimentStatus.DONE)
                .build();


        List<Object> results = sortingService.getResults(id);
        if(results != null)
            res.setResults(results);

        if(res.getResults() != null)
            return res;

        return ExperimentsResults.builder()
                .status(ExperimentsResults.ExperimentStatus.REMOVED)
                .build();
    }

    public void deleteExperiments(String id) {
    }

    Integer findTaskPositionInQueue(String id){
        AlgorithmTask task = taskQueue.stream()
                .filter(e->e.getId().equals(id))
                .findFirst()
                .orElse(null);
        if(task == null)
            return null;
        else
            return taskQueue.indexOf(task);
    }

}
