package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;


public class RadixSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) {
        int m = getMax(tab);

        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(tab, exp);
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    int getMax(SortingData data)
    {
        int[] arr = data.getTab();
        int max = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }


    void countSort(SortingData data, int exp)
    {
        int range = 10;

        int length = data.length();

        int[] tab = data.getTab();
        int[] frequency = new int[range];
        int[] sortedValues = new int[length];

        for (int i = 0; i < length; i++) {
            int digit = (tab[i] / exp) % range;
            frequency[digit]++;
        }

        for (int i = 1; i < range; i++) {
            frequency[i] += frequency[i - 1];
        }

        for (int i = length - 1; i >= 0; i--) {
            int digit = (tab[i] / exp) % range;
            sortedValues[frequency[digit] - 1] = tab[i];
            frequency[digit]--;
        }

        System.arraycopy(sortedValues, 0, tab, 0, length);
    }


}