package put.apl.algorithms.sorting.implementation;

import org.springframework.stereotype.Component;
import put.apl.algorithms.sorting.data.SortingData;
import put.apl.algorithms.sorting.SortingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Component("QuickSort")
public class QuickSort implements SortingAlgorithm{
    String pivotStrategy = null;
    Integer medianCount = null;
    int recursionDepth = 0;
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
            case "Median of fixed three":
                int l = left;
                int m = (left+right)/2;
                int r = right-1;
                if ((tab.lessEqual(m, l) && tab.lessEqual(l, r)) || (tab.lessEqual(r, l) && tab.lessEqual(l, m)))
                    return l;
                else if ((tab.lessEqual(l, m) && tab.lessEqual(m, r)) || (tab.lessEqual(r, m) && tab.lessEqual(m, l)))
                    return m;
                return r;
            case "Median" :
                if (medianCount == null) {
                    throw new IllegalStateException("Undefined medianCount parameter for QuickSort");
                }
                SortingData medianData = new SortingData(Arrays.copyOfRange(tab.getTab(), left, right+1));
                medianData.shuffle();

                int tmpl = 0;
                int tmpr = medianCount-1;
                if(right-left < tmpr)
                    tmpr = right-left;
                int desiredp = (tmpl+tmpr)/2;

                int p = -1;
                while(p != desiredp) {
                    tab.escape();
                    p = partition(medianData, tmpl, tmpr, (tmpl+tmpr)/2);
                    if(p < desiredp){
                        tmpl = p+1;
                    }
                    else if (p > desiredp){
                        tmpr = p-1;
                    }
                }
                int val = medianData.getTab()[p];
                tab.setCompCount(tab.getCompCount() + medianData.getCompCount());
                tab.setSwapCount(tab.getSwapCount() + medianData.getSwapCount());
                for(int i = left; i <= right; i++){
                    if(tab.getTab()[i] == val){
                        return i;
                    }
                }
                throw new IllegalStateException("Median pivot exception");

            default:
                throw new IllegalStateException("Illegal pivot Strategy for QuickSort");

        }
    }

    private int partition(SortingData tab, int l, int r, int p) throws InterruptedException {
        int i = l-1;
        for(int j = l; j <= r; j++) {
            tab.escape();
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
            tab.escape();
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
            tab.escape();
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

    private void quickSort(SortingData tab, int l, int r, int recursion) throws InterruptedException {
        recursionDepth = Math.max(recursion, recursionDepth);
        if(l<r){
            int m = partition(tab, l, r, getPivotIndex(tab, l, r));
            quickSort(tab, l, m-1, recursion + 1);
            quickSort(tab, m+1, r, recursion + 1);
        }
    }

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        recursionDepth = 0;
        quickSort(tab, 0, tab.length()-1, 0);

        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .recursionSize(recursionDepth)
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
