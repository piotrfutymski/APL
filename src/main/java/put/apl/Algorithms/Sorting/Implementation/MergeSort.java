package put.apl.Algorithms.Sorting.Implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

@Component("mergeSort")
public class MergeSort implements SortingAlgorithm {

    private void merge(SortingData tab, int leftIndex, int middleIndex, int rightIndex) {
        SortingData mergedPart = new SortingData(new int[rightIndex - leftIndex]);
        int iterator1 = leftIndex;
        int iterator2 = middleIndex;
        int currentIndex = 0;
        while (iterator1 < middleIndex && iterator2 < rightIndex) {
            if (tab.less(iterator1, iterator2)) {
                mergedPart.getTab()[currentIndex] = tab.getTab()[iterator1];
                iterator1++;
            }
            else {
                mergedPart.getTab()[currentIndex] = tab.getTab()[iterator2];
                iterator2++;
            }
            currentIndex++;
        }
        while (iterator1 < middleIndex) {
            mergedPart.getTab()[currentIndex] = tab.getTab()[iterator1];
            iterator1++;
            currentIndex++;
        }
        while (iterator2 < rightIndex) {
            mergedPart.getTab()[currentIndex] = tab.getTab()[iterator2];
            iterator2++;
            currentIndex++;
        }
        System.arraycopy(mergedPart.getTab(), 0, tab.getTab(), leftIndex, rightIndex - leftIndex);
    }

    private void mergeSort(SortingData tab, int leftIndex, int rightIndex)
    {
        if (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            mergeSort(tab, leftIndex, middleIndex);
            mergeSort(tab, middleIndex, rightIndex);
            merge(tab, leftIndex, middleIndex, rightIndex);
        }
    }


    @Override
    public SortingResult sort(SortingData tab) {
        mergeSort(tab, 0, tab.length());
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {

    }
}
