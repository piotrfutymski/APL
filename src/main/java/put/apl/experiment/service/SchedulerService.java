package put.apl.experiment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import put.apl.experiment.dto.AlgorithmFuture;
import put.apl.experiment.dto.ExperimentsResults;
import put.apl.experiment.dto.GraphExperiment;
import put.apl.experiment.dto.SortingExperiment;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@Service
@EnableScheduling
public class SchedulerService {

    Map<String, AlgorithmFuture> futures;

    ExecutorService executorServiceInfinite;
    Long infiniteJobCounter = 0L;
    Long infiniteJobDone = 0L;

    ExecutorService executorServiceFinite;
    Long finiteJobCounter = 0L;
    Long finiteJobDone = 0L;

    public static Long ACCEPTED_TIME_FROM_LAST_CALL_MS = 10L*60L*1000L;     // 10 min

    @Autowired
    SortingService sortingService;

    public SchedulerService(){
        futures = new HashMap<>();
        executorServiceInfinite = Executors.newFixedThreadPool(1);
        executorServiceFinite = Executors.newFixedThreadPool(1);
    }

    public String scheduleSoritng(List<SortingExperiment> experiments, Boolean finite) {
        return scheduleOperation(experiments, finite, e->sortingService.runExperiments(e));
    }

    public String scheduleGraph(List<GraphExperiment> experiments, boolean finite) {
        //TODO
        return scheduleOperation(experiments, finite, e->null);
    }

    public ExperimentsResults getExperimentsResults(String id){
        if(!futures.containsKey(id))
            return new ExperimentsResults(ExperimentsResults.ExperimentStatus.REMOVED);
        AlgorithmFuture algorithmFuture = futures.get(id);
        if(!algorithmFuture.getFuture().isDone()){
            algorithmFuture.setLastCallForResult(Date.from(Instant.now()));
            if(getQueuePosition(algorithmFuture) <= 0L){
                return ExperimentsResults.builder()
                        .status(ExperimentsResults.ExperimentStatus.CALCULATING)
                        .queuePosition(0L)
                        .build();
            }else{
                return ExperimentsResults.builder()
                        .status(ExperimentsResults.ExperimentStatus.QUEUED)
                        .queuePosition(getQueuePosition(algorithmFuture))
                        .build();
            }
        }else{
            ExperimentsResults res;
            if(algorithmFuture.getExpired())
                res = new ExperimentsResults(ExperimentsResults.ExperimentStatus.EXPIRED);
            else {
                try{
                    res = ExperimentsResults.builder()
                            .status(ExperimentsResults.ExperimentStatus.DONE)
                            .results(algorithmFuture.getFuture().get())
                            .build();
                }catch (Exception e){
                    res = new ExperimentsResults(ExperimentsResults.ExperimentStatus.ERROR);
                    res.setErrorCause(e.toString());
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

    private  <T> String scheduleOperation(List<T> experiments, Boolean finite, CheckedFunction<List<T>, List<Object>> operation) {
        if(experiments.size() == 0)
            return null;
        String id = UUID.randomUUID().toString();
        ExecutorService executorService = finite ? executorServiceFinite : executorServiceInfinite;
        Integer timeout = finite ? AlgorithmFuture.DEFAULT_TIMEOUT_MS : AlgorithmFuture.INFINITE_TIMEOUT;

        AlgorithmFuture algorithmFuture = AlgorithmFuture
                .builder()
                .lastCallForResult(Date.from(Instant.now()))
                .expired(false)
                .timeout(timeout)
                .finite(finite)
                .jobNumber(finite ? infiniteJobCounter++ : finiteJobCounter)
                .build();

        Future<List<Object>> future = executorService.submit(()->{
            List<Object> res = null;
            try{
                algorithmFuture.setStart(Date.from(Instant.now()));
                res = operation.apply(experiments);
                incrementJobDone(algorithmFuture);
            }catch(Exception e){
                incrementJobDone(algorithmFuture);
                throw e;
            }
            return res;
        });

        algorithmFuture.setFuture(future);

        futures.put(id, algorithmFuture);
        return id;
    }

    private void incrementJobDone(AlgorithmFuture future){
        if(future.getFinite())
            finiteJobDone++;
        else
            infiniteJobDone++;
    }

    private Long getQueuePosition(AlgorithmFuture future){
        if(future.getFinite())
            return future.getJobNumber() - finiteJobDone;
        else
            return future.getJobNumber() - infiniteJobDone;
    }

    private void cancel(AlgorithmFuture future){
        boolean isDone = future.getFuture().isDone();
        if(!isDone){
            future.getFuture().cancel(true);
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

    @FunctionalInterface
    private interface CheckedFunction<T, R> {
        R apply(T t) throws InterruptedException;
    }
}
