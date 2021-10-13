package put.apl.Algorithms.Sorting.Data;

import put.apl.Algorithms.Sorting.Implementation.MergeSort;

import java.util.Random;

public class AscendingMaxDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) {
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
        tab.setCompCount(0);
        tab.setSwapCount(0);
        return tab;
    }
}

