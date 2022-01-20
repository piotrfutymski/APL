package put.apl.experiment.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import put.apl.algorithms.sorting.data.DataGeneratorConfig;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.data.SortingDataGenerator;
import put.apl.algorithms.sorting.implementation.SortingAlgorithm;
import put.apl.algorithms.sorting.SortingResult;
import put.apl.experiment.dto.AlgorithmFuture;
import put.apl.experiment.dto.SortingExperiment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SortingService {

    private static final int REPEAT_COUNT = 10;

    ApplicationContext context;
    GarbageCollectorFighter garbageCollectorFighter;

    public List<Object> runExperiments(List<SortingExperiment> experiments, AlgorithmFuture algorithmFuture) throws InterruptedException {
        List<Object> res = new ArrayList<>();
        List<List<SortingExperiment>> results = new ArrayList<>();
        List<SortingExperiment> bannedExperiments = new ArrayList<>();
        for (int i = 0; i < REPEAT_COUNT; i++) {
            results.add(runExperimentsOnce(experiments, bannedExperiments, algorithmFuture));
            algorithmFuture.setDonePercentInfo((float)(i + 1) / REPEAT_COUNT);
        }
        for (int i = 0; i < experiments.size(); i++) {
            List<SortingExperiment> resultsForIExperiment = new ArrayList<>();
            boolean t_is_minus = false;
            for (int j = 0; j < REPEAT_COUNT; j++) {
                if(results.get(j).get(i).getTimeInMillis() < 0)
                    t_is_minus = true;
                resultsForIExperiment.add(results.get(j).get(i));
            }
            if(!t_is_minus)
                res.add(averageExperiments(resultsForIExperiment));
            else
                res.add(results.get(0).get(i));
        }
        return res;
    }

    public SortingExperiment averageExperiments(List<SortingExperiment> experiments){
        Long comparisonCount = experiments.stream().collect(Collectors.averagingLong(e->e.getSortingResult().getComparisonCount())).longValue();
        Long swapCount = experiments.stream().collect(Collectors.averagingLong(e->e.getSortingResult().getSwapCount())).longValue();
        Integer recursionSize = null;
        if(experiments.get(0).getSortingResult().getRecursionSize() != null)
            recursionSize = experiments.stream().collect(Collectors.averagingInt(e->e.getSortingResult().getRecursionSize())).intValue();

        Double timeInMillis = garbageCollectorFighter.getTime(experiments.stream().map(SortingExperiment::getTimeInMillis).collect(Collectors.toList()));
        return SortingExperiment
                .builder()
                .algorithmName(experiments.get(0).getAlgorithmName())
                .algorithmParams(experiments.get(0).getAlgorithmParams())
                .dataDistribution(experiments.get(0).getDataDistribution())
                .maxValue(experiments.get(0).getMaxValue())
                .N(experiments.get(0).getN())
                .timeInMillis(timeInMillis)
                .sortingResult(SortingResult.builder()
                        .comparisonCount(comparisonCount)
                        .swapCount(swapCount)
                        .recursionSize(recursionSize)
                        .build())
                .build();
    }

    private List<SortingExperiment> runExperimentsOnce(List<SortingExperiment> experiments,
                                                       List<SortingExperiment> bannedExperiments,
                                                       AlgorithmFuture algorithmFuture) throws InterruptedException {
        List<SortingExperiment> res = new ArrayList<>();
        float experimentTimeout = (AlgorithmFuture.DEFAULT_TIMEOUT_MS * 3) / ((float)REPEAT_COUNT * experiments.size());
        float begPercent = algorithmFuture.getDonePercentInfo();
        int i = 0;
        List<List<SortingExperiment>> groupedExperiments =
                new ArrayList<>(
                        experiments.stream()
                                .collect(Collectors.groupingBy(SortingExperiment::dataGeneratorGroupingString))
                                .values()
                );
        groupedExperiments = groupedExperiments.stream().sorted(Comparator.comparingInt(e -> e.get(0).getN() )).collect(Collectors.toList());
        for (List<SortingExperiment> groupedExperiment : groupedExperiments) {
            SortingData data = generateDataFor(groupedExperiment.get(0));
            SortingData toSort = new SortingData((data.getTab().clone()));
            for (SortingExperiment sortingExperiment : groupedExperiment) {
                res.add(runExperiment(sortingExperiment, toSort, bannedExperiments, experimentTimeout));
                toSort.restoreFromTemplate(data);
                i++;
                algorithmFuture.setDonePercentInfo(begPercent + (float)i/(experiments.size()*REPEAT_COUNT));
            }
        }

        return res;
    }

    private SortingExperiment runExperiment(SortingExperiment e,
                                            SortingData data,
                                            List<SortingExperiment> bannedExperiments,
                                            float experimentTimeout) throws InterruptedException {
        if(bannedExperiments.stream().anyMatch(
                b->  b.getAlgorithmName().equals(e.getAlgorithmName()) &&
                            b.getMaxValue().equals(e.getMaxValue()) &&
                            b.getDataDistribution().equals(e.getDataDistribution()) &&
                            b.getN() < e.getN())) {
            SortingExperiment res = e.clone();
            res.setTimeInMillis(-1.0);
            return res;
        }
        SortingAlgorithm algorithm = (SortingAlgorithm) context.getBean(e.getAlgorithmName());
        if(e.getAlgorithmParams() != null && !e.getAlgorithmParams().containsKey("maxValue"))
            e.getAlgorithmParams().putAll(Map.of("maxValue", e.getMaxValue().toString()));
        else if(e.getAlgorithmParams() == null)
            e.setAlgorithmParams(Map.of("maxValue", e.getMaxValue().toString()));
        algorithm.setParams(e.getAlgorithmParams());
        long start = System.nanoTime();
        SortingResult result = algorithm.sort(data);
        long end = System.nanoTime();
        double t = (end-start)/1000000.0;
        SortingExperiment res = e.clone();
        res.setSortingResult(result);
        res.setTimeInMillis(t);
        if(t > experimentTimeout){
            bannedExperiments.add(e);
            res.setTimeInMillis(-1.0);
            return res;
        }
        return res;
    }

    private SortingData generateDataFor(SortingExperiment sortingExperiment) throws InterruptedException {
        DataGeneratorConfig config = DataGeneratorConfig.builder()
                .maxValue(sortingExperiment.getMaxValue())
                .N(sortingExperiment.getN())
                .build();
        SortingDataGenerator generator = (SortingDataGenerator) context.getBean(sortingExperiment.getDataDistribution());
        return generator.generate(config);
    }


    public String[] getPossibleSortingAlgorithms() {
        return context.getBeanNamesForType(SortingAlgorithm.class);
    }

    public String[] getPossibleDataDistributions() {
        return context.getBeanNamesForType(SortingDataGenerator.class);
    }
}
