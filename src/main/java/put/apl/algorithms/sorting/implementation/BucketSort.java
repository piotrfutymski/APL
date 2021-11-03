package put.apl.Algorithms.Sorting.Implementation;
import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("Bucket Sort")
public class BucketSort implements SortingAlgorithm {

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        int max = Integer.MIN_VALUE;
        final int numberOfBuckets = (int) Math.sqrt(tab.length());
        for (int i : tab.getTab()){
            tab.escape();
            max = Math.max(i, max);
        }
        List<List<Integer>> buckets = new ArrayList<>();
        for(int i = 0; i < numberOfBuckets; i++){
            tab.escape();
            buckets.add(new ArrayList<>());
        }

        for (int i : tab.getTab()) {
            tab.escape();
            buckets.get(getBucket(i, max, numberOfBuckets)).add(i);
        }
        for(List<Integer> bucket  : buckets){
            tab.escape();
            bucket.sort(null);
        }
        fillReturnTab(tab.getTab(), buckets);
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {

    }

    private int getBucket(int i, int max, int numberOfBuckets) {
        return (int) ((double) i / max * (numberOfBuckets - 1));
    }
    private void  fillReturnTab(int[] returnTab, List<List<Integer>> buckets) {
        int i=0;
        for(List<Integer> bucket  : buckets){
            for (Integer number : bucket)
            {
                returnTab[i] = number;
                i+=1;
            }
        }
    }
}