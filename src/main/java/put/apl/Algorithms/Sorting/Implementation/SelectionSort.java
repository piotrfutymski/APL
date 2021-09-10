package put.apl.Algorithms.Sorting.Implementation;

public class SelectionSort implements SortingAlgorithm {
    @Override
    public void sort(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            int index = i;
            for (int j = i; j < tab.length; j++) {
                if(tab[j] < tab[index])
                    index = j;
            }
            int tmp = tab[i];
            tab[i] = tab[index];
            tab[index] = tmp;
        }
    }
}
