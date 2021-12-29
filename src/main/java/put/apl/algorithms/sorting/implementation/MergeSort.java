package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Map;

@Component("Merge Sort")
public class MergeSort implements SortingAlgorithm {

    private int recursionDepth = 0;

    private void merge(SortingData tab, int leftIndex, int middleIndex, int rightIndex) throws InterruptedException {
        SortingData mergedPart = new SortingData(new int[rightIndex - leftIndex]);
        int iterator1 = leftIndex;
        int iterator2 = middleIndex;
        int currentIndex = 0;
        while (iterator1 < middleIndex && iterator2 < rightIndex) {
            tab.escape();
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
            tab.escape();
            mergedPart.getTab()[currentIndex] = tab.getTab()[iterator1];
            iterator1++;
            currentIndex++;
        }
        while (iterator2 < rightIndex) {
            tab.escape();
            mergedPart.getTab()[currentIndex] = tab.getTab()[iterator2];
            iterator2++;
            currentIndex++;
        }
        System.arraycopy(mergedPart.getTab(), 0, tab.getTab(), leftIndex, rightIndex - leftIndex);
    }

    private void mergeSort(SortingData tab, int leftIndex, int rightIndex, int recursion) throws InterruptedException {
        recursionDepth = Math.max(recursion, recursionDepth);
        if (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            mergeSort(tab, leftIndex, middleIndex, recursion + 1);
            mergeSort(tab, middleIndex, rightIndex, recursion + 1);
            merge(tab, leftIndex, middleIndex, rightIndex);
        }
    }


    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        recursionDepth = 0;
        mergeSort(tab, 0, tab.length(), 0);
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .recursionSize(recursionDepth)
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {

    }
}
