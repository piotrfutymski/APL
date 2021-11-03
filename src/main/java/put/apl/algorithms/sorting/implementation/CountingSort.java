package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Counting Sort")
public class CountingSort implements SortingAlgorithm {

    private Integer maxValue = null;

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        int[] counters = new int[this.maxValue + 1];
        for (int i = 0; i < tab.length(); i++) {
            tab.escape();
            counters[tab.getTab()[i]]++;
        }
        int j = 0;
        for (int i = 0; i < counters.length; i++) {
            while (counters[i] > 0) {
                tab.escape();
                counters[i]--;
                tab.getTab()[j++] = i;
            }
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("maxValue"))
            maxValue = Integer.parseInt(params.get("maxValue"));
    }
}
