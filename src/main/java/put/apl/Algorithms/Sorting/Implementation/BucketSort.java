package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

public class BucketSort implements SortingAlgorithm {

    int nParameter = -1;

    @Override
    public SortingResult sort(SortingData tab) {
        for (int i = 0; i < tab.length(); i++) {
            int index = i;
            for (int j = i; j < tab.length(); j++) {
                if(tab.less(j,index))
                    index = j;
            }
            tab.swap(i, index);
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("n"))
            nParameter = Integer.parseInt(params.get("n"));
    }
}