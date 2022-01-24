package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;
@Component("Bubble Sort Opt. 2")
public class BubbleSortVeryAware implements SortingAlgorithm  {
    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        int firstSortPosition = 0;
        int lastSortPosition = tab.length() - 1;
        for (int i = 0; i < tab.length() - 1; i++) {
            int temp=0;
            boolean sorted = true;
            for (int j = firstSortPosition; j < lastSortPosition; j++) {
                tab.escape();
                if (tab.less(j+1, j)) {
                    tab.swap(j+1, j);
                    if (sorted)
                        firstSortPosition = j;
                    sorted = false;
                    temp = j;
                }
            }
            if (firstSortPosition > 0) firstSortPosition--;
            lastSortPosition = temp;
            if (sorted)
                break;
        }

        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {

    }
}