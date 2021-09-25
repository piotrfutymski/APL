package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class InsertionSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) {
        for (int i = 1; i < tab.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (tab.less(j + 1, j)) {
                    tab.swap(j + 1, j);
                }
                else {
                    break;
                }
            }
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}
