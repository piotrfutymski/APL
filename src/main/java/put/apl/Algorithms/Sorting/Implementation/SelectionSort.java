package put.apl.Algorithms.Sorting.Implementation;

import put.apl.Algorithms.Sorting.SortingResult;

public class SelectionSort implements SortingAlgorithm {
    @Override
    public SortingResult sort(int[] tab) {

        Integer compNum = 0;
        Integer swapNum = 0;

        for (int i = 0; i < tab.length; i++) {
            int index = i;
            for (int j = i; j < tab.length; j++) {
                compNum++;
                if(tab[j] < tab[index])
                    index = j;
            }
            swapNum++;
            int tmp = tab[i];
            tab[i] = tab[index];
            tab[index] = tmp;
        }
        return SortingResult.builder()
                .comparisonCount(compNum)
                .swapCount(swapNum)
                .build();
    }
}
