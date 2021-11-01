package put.apl.Algorithms.Sorting.Data;

import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Implementation.MergeSort;

import java.util.Random;

@Component("VShaped Data Generator")
public class VShapeDataGenerator implements  SortingDataGenerator{
    @Override
    public SortingData generate(DataGeneratorConfig config) throws InterruptedException {
        int size = config.getN();
        int size_first = size / 2;
        int size_second = size / 2;

        if (size % 2 == 1)
            size_first +=1;
        int[] data_first = new Random()
                .ints(1, config.getMaxValue() + 1)
                .limit(size_first)
                .toArray();
        int[] data_second = new Random()
                .ints(1, config.getMaxValue() + 1)
                .limit(size_second)
                .toArray();

        SortingData tab_first = new SortingData(data_first);
        SortingData tab_second = new SortingData(data_second);

        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(tab_first);
        mergeSort.sort(tab_second);

        int[] data_final = new int[size];
        for (int i = 0 ; i < size_first ; i++)
            data_final[i] = data_second[size_first - i - 1];
        if (size_second > 0) System.arraycopy(tab_second.getTab(), 0, data_final, size_first, size_second);
        return new SortingData(data_final);

    }
}
