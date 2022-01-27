package put.apl.algorithms.sorting.data;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.implementation.MergeSort;

import java.util.Random;

@Component("Increasing Data With Max at Beginning")
public class IncreasingMaxDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) throws InterruptedException {
        int[] data = new Random()
                .ints(0, config.getMaxValue())
                .limit(config.getN())
                .toArray();
        // Creating ascending data
        SortingData tab = new SortingData(data);
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(tab);
        // Set first element to max
        tab.getTab()[0] = config.getMaxValue() + 1;
        // Cleanup
        tab.setCompCount(0L);
        tab.setSwapCount(0L);
        return tab;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

