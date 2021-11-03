package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Selection Sort")
public class SelectionSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        for (int i = 0; i < tab.length(); i++) {
            int index = i;
            for (int j = i; j < tab.length(); j++) {
                tab.escape();
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

    @Override
    public void setParams(Map<String, String> params) {

    }
}
