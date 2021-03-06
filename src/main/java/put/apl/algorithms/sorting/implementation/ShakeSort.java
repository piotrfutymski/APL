package put.apl.algorithms.sorting.implementation;
import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Shake Sort")
public class ShakeSort implements SortingAlgorithm {

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        boolean swapped = true;

        for (int i = 0; i < tab.length() - 1; i++) {

            boolean sorted = true;
            for (int j = i; j < tab.length() - i - 1; j++) {
                tab.escape();
                if (tab.less(j+1, j)) {
                    tab.swap(j+1, j);
                    sorted = false;
                }
            }
            if (sorted)
                break;

            sorted = true;

            for (int j = tab.length() - i - 1; j > i; j--) {
                tab.escape();
                if (tab.less(j, j-1)) {
                    tab.swap(j, j-1);
                    sorted = false;
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

    @Override
    public void setParams(Map<String, String> params) {

    }
}