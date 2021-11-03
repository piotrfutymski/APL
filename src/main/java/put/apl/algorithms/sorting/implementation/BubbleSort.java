package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;
@Component("Bubble Sort")
public class BubbleSort implements SortingAlgorithm  {
    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        for (int i = 0; i < tab.length() - 1; i++) {
            for (int j = 0; j < tab.length() - i - 1; j++) {
                tab.escape();
                if (tab.less(j+1, j))
                    tab.swap(j+1, j);
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
