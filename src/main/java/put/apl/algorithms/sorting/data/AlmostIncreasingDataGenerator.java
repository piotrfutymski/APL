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
        if (elementsNotInOrder < 1) {
            elementsNotInOrder = 1;
        }
        for (int i = 0; i < elementsNotInOrder; i++) {
            int randomMin = data.length() / (elementsNotInOrder) - (data.length() / (elementsNotInOrder - 1));
            int randomMax = data.length() / (elementsNotInOrder);
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
