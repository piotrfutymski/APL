package put.apl.Algorithms.Sorting.Implementation;

import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

@Component("quickSort")
public class QuickSort implements SortingAlgorithm{
    Integer pivotStrategy = null;
    private int getPivotIndex(SortingData tab, int left, int right) throws InterruptedException {
        switch (pivotStrategy){
            case 0://FIRST
                return left;
            case 1://MID
                return (left+right)/2;
            case 2://LAST
                return right-1;
            case 3://RAND
                return (int)(Math.random() * (right-left) + left);
            case 4:
                int l = left;
                int m = (left+right)/2;
                int r = right-1;
                if (!tab.lessEqual(l, m) ^ !tab.lessEqual(l, r))
                    return l;
                else if (tab.less(m, l) ^ !tab.less(m, r))
                    return m;
                else
                    return r;
            default:
                throw new IllegalStateException("Illegal pivot Strategy for QuickSort");

        }
    }

    private int partition(SortingData tab, int l, int r) throws InterruptedException {
        int p = getPivotIndex(tab, l, r);
        int i = l-1;
        for(int j = l; j < r; j++) {
            if (tab.less(j, p)) {
                tab.swap(++i, j);
                if(i==p){
                    p=j;
                }
            }
        }
        tab.swap(++i, p);
        return i;
    }

    private void quickSort(SortingData tab, int l, int r) throws InterruptedException {
        if(l<r){
            int m = partition(tab, l, r);
            quickSort(tab, l, m);
            quickSort(tab, m, r);
        }
    }

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        quickSort(tab, 0, tab.length());
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("pivotStrategy"))
            pivotStrategy = Integer.parseInt(params.get("pivotStrategy"));
    }
}
