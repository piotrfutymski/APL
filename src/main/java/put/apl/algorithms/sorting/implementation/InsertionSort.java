package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Insertion Sort")
public class InsertionSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        for (int i = 1; i < tab.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                tab.escape();
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

    @Override
    public void setParams(Map<String, String> params) {

    }
}
