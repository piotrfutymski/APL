package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

public class BucketSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) {
        for (int i = 0; i < tab.length(); i++) {
            int index = i;
            for (int j = i; j < tab.length(); j++) {
                if(tab.less(j,index))
                    index = j;
            }
            tab.swap(i, index);
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }
}