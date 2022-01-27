package put.apl.algorithms.sorting.data;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.implementation.MergeSort;

import java.util.Random;

@Component("Decreasing Data")
public class DecreasingDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) throws InterruptedException {
        int[] tab = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();

        SortingData data = new SortingData(tab);
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(data);

        for (int i=0; i<data.length()/2; i++)
            data.swap(i, data.length() - 1 - i);

        data.setCompCount(0L);
        data.setSwapCount(0L);

        return data;
    }
    @Override
    public String getDescription() {
        return null;
    }
}
