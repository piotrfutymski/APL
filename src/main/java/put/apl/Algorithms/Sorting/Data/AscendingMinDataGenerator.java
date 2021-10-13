package put.apl.Algorithms.Sorting.Data;

import put.apl.Algorithms.Sorting.Implementation.MergeSort;

import java.util.Random;

public class AscendingMinDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] data = new Random()
                .ints(1, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();
        // Creating ascending data
        SortingData tab = new SortingData(data);
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(tab);
        // Set last element to zero (min)
        tab.getTab()[tab.length() - 1] = 0;
        // Cleanup
        tab.setCompCount(0);
        tab.setSwapCount(0);
        return tab;
    }
}

