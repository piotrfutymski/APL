package put.apl.Utility;

import put.apl.Algorithms.Sorting.Data.SortingData;

public class HeapUtility {

    static public void buildHeap(SortingData tab, int startIndex, int lastIndex) throws InterruptedException {
        int leftChildIndex = startIndex * 2 + 1;
        int rightChildIndex = startIndex * 2 + 2;
        int maxElement = startIndex;
        if (leftChildIndex < lastIndex) {
            buildHeap(tab, leftChildIndex, lastIndex);
        }
        if (rightChildIndex < lastIndex) {
            buildHeap(tab, rightChildIndex, lastIndex);
        }
        if (leftChildIndex < lastIndex && tab.less(startIndex, leftChildIndex))
        {
            maxElement = leftChildIndex;
        }
        if (rightChildIndex < lastIndex && tab.less(maxElement, rightChildIndex))
        {
            maxElement = rightChildIndex;
        }
        tab.swap(maxElement, startIndex);
    }

}
