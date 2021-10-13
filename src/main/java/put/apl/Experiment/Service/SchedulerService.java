package put.apl.Experiment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import put.apl.Experiment.Dto.AlgorithmFuture;
import put.apl.Experiment.Dto.ExperimentsResults;
import put.apl.Experiment.Dto.GraphExperiment;
import put.apl.Experiment.Dto.SortingExperiment;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@Service
@EnableScheduling
public class SchedulerService {

    Map<String, AlgorithmFuture> futures;

    ExecutorService executorServiceInfinite;

    ExecutorService executorServiceFinite;

    public static int ACCEPTED_TIME_FROM_LAST_CALL_MS = 10*60*1000;     // 10 min

    @Autowired
    SortingService sortingService;

    public SchedulerService(){
        futures = new HashMap<>();
        executorServiceInfinite = Executors.newFixedThreadPool(1);
        executorServiceFinite = Executors.newFixedThreadPool(1);
    }

    public static void moveOnePositionInQueue(Map<String, AlgorithmFuture> futures, int timeout){
        if(!Thread.interrupted())
            futures.values()
                .stream()
                .filter(e->e.getTimeout() == timeout)
                .forEach(v->v.setPosition(v.getPosition()-1));
    }

    public String scheduleSoritng(List<SortingExperiment> experiments, boolean finite) {
        if(experiments.size() == 0)
            return null;
        String id = UUID.randomUUID().toString();
        ExecutorService executorService = finite ? executorServiceFinite : executorServiceInfinite;
        int timeout = finite ? AlgorithmFuture.DEFAULT_TIMEOUT_MS : AlgorithmFuture.INFINITE_TIMEOUT;
        Future<List<Object>> future = executorService.submit(()->{
            List<Object> res = sortingService.runExperiments(experiments);
            moveOnePositionInQueue(futures, timeout);
            return res;
        });
        Date now = Date.from(Instant.now());
        AlgorithmFuture algorithmFuture = AlgorithmFuture
                .builder()
                .lastCallForResult(now)
                .future(future)
                .expired(false)
                .timeout(timeout)
                .position(getTaskIndex(executorService))
                .build();
        futures.put(id, algorithmFuture);
        return id;
    }

    public String scheduleGraph(List<GraphExperiment> experiments, boolean finite) {
        return null;
        //TODO
    }

    public ExperimentsResults getExperimentsResults(String id){
        if(!futures.containsKey(id))
            return new ExperimentsResults(ExperimentsResults.ExperimentStatus.REMOVED);
        AlgorithmFuture algorithmFuture = futures.get(id);
        if(!algorithmFuture.getFuture().isDone()){
            algorithmFuture.setLastCallForResult(Date.from(Instant.now()));
            if(algorithmFuture.getPosition() <= 0L){
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
            ExperimentsResults res;

            if(algorithmFuture.isExpired())
                res = new ExperimentsResults(ExperimentsResults.ExperimentStatus.EXPIRED);
            else {
                try{
                    res = ExperimentsResults.builder()
                            .status(ExperimentsResults.ExperimentStatus.DONE)
                            .results(algorithmFuture.getFuture().get())
                            .build();
                }catch (Exception e){
                    res = new ExperimentsResults(ExperimentsResults.ExperimentStatus.ERROR);
                }

            }
            futures.remove(id);
            return res;
        }
    }

    public void deleteExperiments(String id) {
        if(futures.containsKey(id)){
            cancel(futures.get(id));
            futures.remove(id);
        }
    }

    void cancel(AlgorithmFuture future){
        boolean isDone = future.getFuture().isDone();
        if(!isDone){
            future.getFuture().cancel(true);
            moveOnePositionInQueue(futures, future.getTimeout());
        }
    }

    @Scheduled(cron = "0/5 * * * * *")
    private void clearTasks(){
        Date now = Date.from(Instant.now());
        futures.entrySet()
                .removeIf(entry -> {
                    if((now.getTime() - entry.getValue().getLastCallForResult().getTime()) > ACCEPTED_TIME_FROM_LAST_CALL_MS)
                    {
                        cancel(entry.getValue());
                        return true;
                    }
                    return false;
                });
        futures.forEach((key, value) -> {
            if(value.getPosition() == 0 && value.getStart()==null)
                value.setStart(Date.from(Instant.now()));
            if (
                    value.getTimeout() != AlgorithmFuture.INFINITE_TIMEOUT &&
                    value.getStart() != null &&
                    now.getTime() - value.getStart().getTime() > value.getTimeout()
            ) {
                cancel(value);
                value.setExpired(true);
            }
        });

    }

    private long getTaskIndex(ExecutorService executorService){
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        long taskCount = threadPoolExecutor.getTaskCount();
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        long res = taskCount - completedTaskCount - 1;
        return res;
    }

}
