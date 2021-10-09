package put.apl.Algorithms.Sorting.Data;

import put.apl.Utility.HeapUtility;

import java.util.Random;

public class RandomHeapDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) {
        int[] heapData = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();
        SortingData tab = new SortingData(heapData);
        HeapUtility.buildHeap(tab, 0, tab.length());
        return tab;
    }
}
