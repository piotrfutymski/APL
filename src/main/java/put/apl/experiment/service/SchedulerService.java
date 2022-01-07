package put.apl.experiment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import put.apl.experiment.dto.AlgorithmFuture;
import put.apl.experiment.dto.ExperimentsResults;
import put.apl.experiment.dto.GraphExperiment;
import put.apl.experiment.dto.SortingExperiment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@Service
@EnableScheduling
public class SchedulerService {

    Map<String, AlgorithmFuture> futures;

    ThreadPoolExecutor executorServiceInfinite;
    Long infiniteJobCounter = 0L;
    Long infiniteJobDone = 0L;

    ThreadPoolExecutor executorServiceFinite;
    Long finiteJobCounter = 0L;
    Long finiteJobDone = 0L;

    public static Integer EXPERIMENTS_CACHED = 1000;

    @Autowired
    SortingService sortingService;
    @Autowired
    GraphService graphService;

    public SchedulerService(){
        futures = new HashMap<>();
        executorServiceInfinite = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        executorServiceFinite = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
    }

    public String scheduleSoritng(List<SortingExperiment> experiments, Boolean finite) {
        return scheduleOperation(experiments, finite, (e,f)->sortingService.runExperiments(e,f));
    }

    public String scheduleGraph(List<GraphExperiment> experiments, Boolean finite) {
        return scheduleOperation(experiments, finite, (e,f)->graphService.runExperiments(e,f));
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
                        .donePercent(algorithmFuture.getDonePercentInfo())
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
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    res.setErrorCause(e.toString() + "\n" + sw.toString());
                }
            }
            return res;
        }
    }

    public void deleteExperiments(String id) {
        if(futures.containsKey(id)){
            cancel(futures.get(id));
            futures.remove(id);
        }
    }

    private  <T> String scheduleOperation(List<T> experiments, Boolean finite, CheckedFunction<List<T>, AlgorithmFuture, List<Object>> operation) {
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
                .donePercentInfo(0.0f)
                .jobNumber(finite ? finiteJobCounter++ : infiniteJobCounter++)
                .build();

        Future<List<Object>> future = executorService.submit(()->{
            List<Object> res = null;
            try{
                algorithmFuture.setStart(Date.from(Instant.now()));
                res = operation.apply(experiments, algorithmFuture);
                incrementJobDone(algorithmFuture);
                algorithmFuture.setDonePercentInfo(1.0f);
            }catch(Throwable e){
                incrementJobDone(algorithmFuture);
                throw e;
            }
            return res;
        });

        algorithmFuture.setFuture(future);

        futures.put(id, algorithmFuture);
        if(futures.size() > EXPERIMENTS_CACHED){
            futures.entrySet()
                    .stream()
                    .filter(e->Objects.nonNull(e.getValue().getStart()))
                    .sorted(Comparator.comparingLong(e->e.getValue().getStart().getTime()))
                    .limit(futures.size() - EXPERIMENTS_CACHED)
                    .map(Map.Entry::getKey)
                    .forEach(e->futures.remove(e));
        }
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
            if(getQueuePosition(future) > 0)
                incrementJobDone(future);
            future.getFuture().cancel(true);
        }
    }

    @Scheduled(cron = "0/5 * * * * *")
    private void clearTasks(){
        Date now = Date.from(Instant.now());
        futures.forEach((key, value) -> {
            if (
                    value.getTimeout() != AlgorithmFuture.INFINITE_TIMEOUT &&
                    value.getStart() != null &&
                    (now.getTime() - value.getStart().getTime() > value.getTimeout()) &&
                    !value.getFuture().isDone()
            ) {
                cancel(value);
                value.setExpired(true);
            }
            if(
                    (now.getTime() - value.getLastCallForResult().getTime() > AlgorithmFuture.DEFAULT_LASTCALL_MS) &&
                    !value.getFuture().isDone()
            ){
                cancel(value);
                value.setExpired(true);
            }
        });

    }

    @FunctionalInterface
    private interface CheckedFunction<T, U, R> {
        R apply(T t, U u) throws InterruptedException;
    }
}
