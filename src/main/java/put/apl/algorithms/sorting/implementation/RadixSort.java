package put.apl.algorithms.sorting.implementation;
import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Radix Sort")
public class RadixSort implements SortingAlgorithm {

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        int m = getMax(tab);

        for (int exp = 1; m / exp > 0; exp *= 10){
            tab.escape();
            countSort(tab, exp);
        }

        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {

    }

    int getMax(SortingData data) throws InterruptedException {
        int[] arr = data.getTab();
        int max = arr[0];
        for (int i = 1; i < arr.length; i++){
            data.escape();
            if (arr[i] > max)
                max = arr[i];
        }

        return max;
    }


    void countSort(SortingData data, int exp) throws InterruptedException {
        int range = 10;

        int length = data.length();

        int[] tab = data.getTab();
        int[] frequency = new int[range];
        int[] sortedValues = new int[length];

        for (int i = 0; i < length; i++) {
            data.escape();
            int digit = (tab[i] / exp) % range;
            frequency[digit]++;
        }

        for (int i = 1; i < range; i++) {
            data.escape();
            frequency[i] += frequency[i - 1];
        }

        for (int i = length - 1; i >= 0; i--) {
            data.escape();
            int digit = (tab[i] / exp) % range;
            sortedValues[frequency[digit] - 1] = tab[i];
            frequency[digit]--;
        }

        System.arraycopy(sortedValues, 0, tab, 0, length);
    }
}