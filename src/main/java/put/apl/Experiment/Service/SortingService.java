package put.apl.Experiment.Service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import put.apl.Algorithms.Sorting.Data.DataGeneratorConfig;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.Data.SortingDataGenerator;
import put.apl.Algorithms.Sorting.Implementation.SortingAlgorithm;
import put.apl.Algorithms.Sorting.SortingResult;
import put.apl.Experiment.Dto.SortingExperiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SortingService {

    private static final int REPEAT_COUNT = 5;

    ApplicationContext context;

    public List<Object> runExperiments(List<SortingExperiment> experiments) throws InterruptedException {

        List<Object> res = new ArrayList<>();
        List<List<SortingExperiment>> results = new ArrayList<>();
        for (int i = 0; i < REPEAT_COUNT; i++) {
            results.add(runExperimentsOnce(experiments));
        }
        for (int i = 0; i < experiments.size(); i++) {
            List<SortingExperiment> resultsForIExperiment = new ArrayList<>();
            for (int j = 0; j < REPEAT_COUNT; j++) {
                resultsForIExperiment.add(results.get(j).get(i));
            }
            res.add(averageExperiments(resultsForIExperiment));
        }
        return res;
    }

    public SortingExperiment averageExperiments(List<SortingExperiment> experiments){
        Long comparisonCount = experiments.stream().collect(Collectors.averagingLong(e->e.getSortingResult().getComparisonCount())).longValue();
        Long swapCount = experiments.stream().collect(Collectors.averagingLong(e->e.getSortingResult().getSwapCount())).longValue();
        Integer recursionSize = null;
        if(experiments.get(0).getSortingResult().getRecursionSize() != null)
            recursionSize = experiments.stream().collect(Collectors.averagingInt(e->e.getSortingResult().getRecursionSize())).intValue();
        Double timeInMillis = experiments.stream().collect(Collectors.averagingDouble(SortingExperiment::getTimeInMillis));
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

    private List<SortingExperiment> runExperimentsOnce(List<SortingExperiment> experiments) throws InterruptedException {
        List<SortingExperiment> res = new ArrayList<>();

        List<List<SortingExperiment>> groupedExperiments =
                new ArrayList<>(experiments.stream().collect(Collectors.groupingBy(SortingExperiment::dataGeneratorGroupingString)).values());

        for (List<SortingExperiment> groupedExperiment : groupedExperiments) {
            SortingData data = generateDataFor(groupedExperiment.get(0));
            for (SortingExperiment sortingExperiment : groupedExperiment) {
                res.add(runExperiment(sortingExperiment, new SortingData(data.getTab().clone())));
            }
        }

        return res;
    }

    private SortingExperiment runExperiment(SortingExperiment e, SortingData data) throws InterruptedException {
        SortingAlgorithm algorithm = (SortingAlgorithm) context.getBean(e.getAlgorithmName());
        if(e.getAlgorithmParams() != null && !e.getAlgorithmParams().containsKey("maxValue"))
            e.getAlgorithmParams().putAll(Map.of("maxValue", e.getMaxValue().toString()));
        else
            e.setAlgorithmParams(Map.of("maxValue", e.getMaxValue().toString()));
        algorithm.setParams(e.getAlgorithmParams());
        long start = System.nanoTime();
        SortingResult result = algorithm.sort(data);
        long end = System.nanoTime();
        SortingExperiment res = e.clone();
        res.setSortingResult(result);
        res.setTimeInMillis((double)(end-start)/1000000.0);
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
