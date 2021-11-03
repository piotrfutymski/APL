package put.apl.utility;

import put.apl.algorithms.sorting.data.SortingData;

public class HeapUtility {

    static public void buildHeap(SortingData tab, int startIndex, int lastIndex) throws InterruptedException {
        int leftChildIndex = startIndex * 2 + 1;
        int rightChildIndex = startIndex * 2 + 2;
        int maxElement = startIndex;
        if (leftChildIndex < lastIndex && tab.less(startIndex, leftChildIndex))
        {
            maxElement = leftChildIndex;
        }
        if (rightChildIndex < lastIndex && tab.less(maxElement, rightChildIndex))
        {
            maxElement = rightChildIndex;
        }
        if (maxElement != startIndex) {
            tab.swap(maxElement, startIndex);
            buildHeap(tab, maxElement, lastIndex);
        }
    }
}
