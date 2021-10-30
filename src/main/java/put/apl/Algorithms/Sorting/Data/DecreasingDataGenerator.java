package put.apl.Algorithms.Sorting.Data;

import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Implementation.MergeSort;

import java.util.Random;

@Component("decreasingData")
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
}
