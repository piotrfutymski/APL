package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class BinaryInsertionSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) {
        // Sorted part of an array <beginIndex; endIndex)
        int beginIndex;
        int endIndex;
        int index = 0;
        for (int i = 1; i < tab.length(); i++) {
            beginIndex = 0;
            endIndex = i;
            while (endIndex - beginIndex > 0) {
                index = (endIndex + beginIndex) / 2;
                if (tab.less(index, i)) {
                    beginIndex = index + 1;
                }
                else {
                    endIndex = index;
                }
            }
            for (int j = i; j > beginIndex; j--) {
                tab.swap(j, j - 1);
            }
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}
