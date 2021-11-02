package put.apl.Algorithms.Sorting.Implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

@Component("Binary Insertion Sort")
public class BinaryInsertionSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        // Sorted part of an array <beginIndex; endIndex)
        int beginIndex;
        int endIndex;
        int index = 0;
        for (int i = 1; i < tab.length(); i++) {
            tab.escape();
            beginIndex = 0;
            endIndex = i;
            while (endIndex - beginIndex > 0) {
                tab.escape();
                index = (endIndex + beginIndex) / 2;
                if (tab.less(index, i)) {
                    beginIndex = index + 1;
                }
                else {
                    endIndex = index;
                }
            }
            for (int j = i; j > beginIndex; j--) {
                tab.escape();
                tab.swap(j, j - 1);
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
