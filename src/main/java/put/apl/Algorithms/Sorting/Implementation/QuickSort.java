package put.apl.Algorithms.Sorting.Implementation;

import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

@Component("quickSort")
public class QuickSort implements SortingAlgorithm{
    String pivotStrategy = null;
    Integer medianCount = null;
    private int getPivotIndex(SortingData tab, int left, int right) throws InterruptedException {
        switch (pivotStrategy){
            case "First item":
                return left;
            case "Middle item":
                return (left+right)/2;
            case "Last item":
                return right;
            case "Random item":
                return (int)(Math.random() * (right-left) + left);
            case "Median of three":
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

    private int partition(SortingData tab, int l, int r, int p) throws InterruptedException {
        int i = l-1;
        for(int j = l; j <= r; j++) {
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

    private int partitionWithMedian(SortingData tab, int l, int r) throws InterruptedException {
        if (medianCount == null) {
            throw new IllegalStateException("Undefined medianCount parameter for QuickSort");
        }
        int tmpl = l;
        int tmpr = l+medianCount-1;
        if(r < tmpr)
            tmpr = r;
        int desiredp = (tmpl+tmpr)/2;

        int p = -1;
        while(p != desiredp) {
            p = partition(tab, tmpl, tmpr, tmpr);
            if(p < desiredp){
                tmpl = p+1;
            }
            else if (p > desiredp){
                tmpr = p-1;
            }
        }
        int i = p-1;
        for(int j = l+medianCount-1; j <= r; j++) {
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
            int m = partition(tab, l, r, getPivotIndex(tab, l, r));
            quickSort(tab, l, m-1);
            quickSort(tab, m+1, r);
        }
    }
    private void quickSortWithMedian(SortingData tab, int l, int r) throws InterruptedException {
        if(l<r){
            int m = partitionWithMedian(tab, l, r);
            quickSort(tab, l, m-1);
            quickSort(tab, m+1, r);
        }
    }

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        if(pivotStrategy == "Median"){
            quickSortWithMedian(tab, 0, tab.length()-1);
        }
        else {
            quickSort(tab, 0, tab.length()-1);
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("pivotStrategy"))
            pivotStrategy = params.get("pivotStrategy");
        if (params.containsKey("medianCount"))
            medianCount = Integer.parseInt(params.get("medianCount"));
    }
}
