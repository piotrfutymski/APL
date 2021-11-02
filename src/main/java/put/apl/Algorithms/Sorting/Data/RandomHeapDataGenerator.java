package put.apl.Algorithms.Sorting.Data;

import org.springframework.stereotype.Component;
import put.apl.Utility.HeapUtility;

import java.util.Random;

@Component("Random Heap Data")
public class RandomHeapDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) throws InterruptedException {
        int[] heapData = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();
        SortingData tab = new SortingData(heapData);
        for (int i = tab.length()/2 - 1; i>= 0; i--) {
            HeapUtility.buildHeap(tab, i, tab.length());
        }
        return tab;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
