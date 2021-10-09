package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class BubbleSortSuperAware implements SortingAlgorithm  {
    @Override
    public SortingResult sort(SortingData tab) {
        for (int i = 0; i < tab.length() - 1; i++) {
            for (int j = 0; j < tab.length() - i - 1; j++) {
                if (tab.less(j+1, j))
                    tab.swap(j+1, j);
            }
        }

        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}