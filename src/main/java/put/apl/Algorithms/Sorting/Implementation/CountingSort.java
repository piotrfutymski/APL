package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;
import put.apl.Utility.HeapUtility;

public class CountingSort implements SortingAlgorithm {

    private final int maxValue;

    public CountingSort(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public SortingResult sort(SortingData tab) {
        int[] counters = new int[this.maxValue + 1];
        for (int i = 0; i < tab.length(); i++) {
            counters[tab.getTab()[i]]++;
        }
        int j = 0;
        for (int i = 0; i < counters.length; i++) {
            while (counters[i] > 0) {
                counters[i]--;
                tab.getTab()[j++] = i;
            }
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}
