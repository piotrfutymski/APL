package put.apl.Experiment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import put.apl.Experiment.Dto.AlgorithmFuture;
import put.apl.Experiment.Dto.ExperimentsResults;
import put.apl.Experiment.Dto.GraphExperiment;
import put.apl.Experiment.Dto.SortingExperiment;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@Service
public class SchedulerService {

    Map<String, AlgorithmFuture> futures;

    ExecutorService executorService;

    @Autowired
    SortingService sortingService;

    public SchedulerService(){
        futures = new HashMap<>();
        executorService = Executors.newFixedThreadPool(1);
    }

    public String scheduleSoritng(List<SortingExperiment> experiments) {
        if(experiments.size() == 0)
            return null;
        String id = UUID.randomUUID().toString();
        Future<List<Object>> future = executorService.submit(()->{
            List<Object> res = sortingService.runExperiments(id,experiments);
            futures.forEach((k, v)->v.setPosition(v.getPosition()-1));
            return res;
        });
        Date now = Date.from(Instant.now());

        AlgorithmFuture algorithmFuture = AlgorithmFuture
                .builder()
                .lastCallForResult(now)
                .start(now)
                .future(future)
                .position(getTaskIndex())
                .build();

        futures.put(id, algorithmFuture);
        return id;
    }

    public String scheduleGraph(List<GraphExperiment> experiments) {
        return null;
    }

    public ExperimentsResults getExperimentsResults(String id) throws ExecutionException, InterruptedException {
        if(!futures.containsKey(id))
            return ExperimentsResults.builder()
                    .status(ExperimentsResults.ExperimentStatus.REMOVED)
                    .build();

        AlgorithmFuture algorithmFuture = futures.get(id);
        if(!algorithmFuture.getFuture().isDone()){
            algorithmFuture.setLastCallForResult(Date.from(Instant.now()));
            if(algorithmFuture.getPosition() == 0L){
                return ExperimentsResults.builder()
                        .status(ExperimentsResults.ExperimentStatus.CALCULATING)
                        .queuePosition(0L)
                        .build();
            }else{
                return ExperimentsResults.builder()
                        .status(ExperimentsResults.ExperimentStatus.QUEUED)
                        .queuePosition(algorithmFuture.getPosition())
                        .build();
            }
        }else{
            futures.remove(id);
            return ExperimentsResults.builder()
                    .status(ExperimentsResults.ExperimentStatus.DONE)
                    .results(algorithmFuture.getFuture().get())
                    .build();
        }
    }

    public void deleteExperiments(String id) {
        if(futures.containsKey(id)){
            futures.get(id).getFuture().cancel(true);
            futures.remove(id);
        }
    }

    private long getTaskIndex(){
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        long taskCount = threadPoolExecutor.getTaskCount();
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        long res = taskCount - completedTaskCount - 1;
        return res;
    }

}
