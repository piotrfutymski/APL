package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Shell Sort")
public class ShellSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        for (int k = tab.length() / 2; k > 0; k /= 2) {
            for (int i = 0; i < tab.length(); i += k) {
                for (int j = i - k; j >= 0; j -= k) {
                    tab.escape();
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

    @Override
    public void setParams(Map<String, String> params) {

    }
}
