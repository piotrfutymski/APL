package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class BubbleSortSuperAware implements SortingAlgorithm  {
    @Override
    public SortingResult sort(SortingData tab) {
        int firstSortPosition = -1;
        int lastSortPosition = tab.length();
        for (int i = 0; i < tab.length() - 1; i++) {

            boolean sorted = true;
            for (int j = Integer.max(0, firstSortPosition); j < lastSortPosition - 1; j++) {
                if (tab.less(j+1, j))
                {
                    tab.swap(j+1, j);
                    sorted = false;
                    if (firstSortPosition == -1)
                        firstSortPosition = j;
                    lastSortPosition = j;
                }
            }
            if (sorted)
                break;
        }

        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}