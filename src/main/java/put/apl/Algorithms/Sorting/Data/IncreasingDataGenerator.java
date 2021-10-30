package put.apl.Algorithms.Sorting.Data;

import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Implementation.MergeSort;

import java.util.Random;

@Component("increasingData")
public class IncreasingDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) throws InterruptedException {
        int[] tab = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();

        SortingData data = new SortingData(tab);
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(data);

        data.setCompCount(0L);
        data.setSwapCount(0L);

        return data;
    }
}
