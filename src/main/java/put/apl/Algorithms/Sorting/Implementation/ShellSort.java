package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class ShellSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) {
        for (int k = tab.length() / 2; k > 0; k /= 2) {
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
