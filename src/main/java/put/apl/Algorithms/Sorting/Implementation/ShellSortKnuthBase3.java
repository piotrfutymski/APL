package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class ShellSortKnuthBase3 implements SortingAlgorithm{
    @Override
    public SortingResult sort(SortingData tab) {
        int initialInterval = 1;
        for (int k = 2; k < Math.floor(Math.log(tab.length()) / Math.log(3)) - 1; k++) {
            initialInterval = initialInterval * 3 + 1;
        }
        for (int k = initialInterval; k > 0; k = (k - 1) / 3) {
            for (int i = 0; i < tab.length(); i += k) {
                for (int j = i - k; j >= 0; j -= k) {
                    if (tab.less(j + k, j)) {
                        tab.swap(j + k, j);
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}
