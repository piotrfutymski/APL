package put.apl.Algorithms.Sorting.Implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.SortingResult;

import java.util.Map;

@Component("Shell Sort Knuth")
public class ShellSortKnuth implements SortingAlgorithm{

    Integer k_param = null;

    @Override
    public SortingResult sort(SortingData tab) throws InterruptedException {
        if(k_param == null)
            throw new IllegalStateException("Parameter k must be set for ShellSortKnuth");

        int initialInterval = 1;
        for (int k = 2; k < Math.floor(Math.log(tab.length()) / Math.log(k_param)) - 1; k++) {
            initialInterval = initialInterval * k_param + 1;
        }
        for (int k = initialInterval; k > 0; k = (k - 1) / k_param) {
            for (int i = 0; i < tab.length(); i += k) {
                for (int j = i - k; j >= 0; j -= k) {
                    if (tab.less(j + k, j)) {
                        tab.swap(j + k, j);
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return SortingResult.builder()
                .comparisonCount(tab.getCompCount())
                .swapCount(tab.getSwapCount())
                .build();
    }

    @Override
    public void setParams(Map<String, String> params) {
        if (params.containsKey("k"))
            k_param = Integer.parseInt(params.get("k"));
    }
}
