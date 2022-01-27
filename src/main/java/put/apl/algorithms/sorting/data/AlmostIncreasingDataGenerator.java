package put.apl.algorithms.sorting.data;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.implementation.MergeSort;

import java.util.Random;

@Component("Almost Increasing Data")
public class AlmostIncreasingDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) throws InterruptedException {
        int[] tab = new Random()
                .ints(0, config.getMaxValue() + 1)
                .limit(config.getN())
                .toArray();

        SortingData data = new SortingData(tab);
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(data);

        int elementsNotInOrder = (int)(data.length() * 0.05);
        for (int i = 1; i <= elementsNotInOrder; i++) {
            int randomMin = data.length() / (elementsNotInOrder) * (i-1);
            int randomMax = data.length() / (elementsNotInOrder) * i;
            data.getTab()[(int) ((Math.random() * (randomMax - randomMin)) + randomMin)] = (int) (Math.random() * config.getMaxValue());
        }
        data.setCompCount(0L);
        data.setSwapCount(0L);

        return data;
    }
    @Override
    public String getDescription() {
        return null;
    }
}
