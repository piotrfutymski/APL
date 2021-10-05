package put.apl.Algorithms.Sorting.Implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;
import put.apl.Utility.HeapUtility;

import java.util.Map;

@Component("heapSort")
public class HeapSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(SortingData tab) {
        HeapUtility.buildHeap(tab, 0, tab.length());
        for (int i = tab.length() - 1; i >= 0; i--) {
            tab.swap(i, 0);
            HeapUtility.buildHeap(tab, 0, i);
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
