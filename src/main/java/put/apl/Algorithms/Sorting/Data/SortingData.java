package put.apl.Algorithms.Sorting.Data;

import lombok.*;

public class SortingData {

    public SortingData(int[] tab){
        this.tab = tab;
    }

    @Getter
    private int[] tab;
    @Getter
    private int compCount = 0;
    @Getter
    private int swapCount = 0;

    public boolean lessEqual(int i, int j){
        compCount++;
        return tab[i] <= tab[j];
    }

    public boolean less(int i, int j){
        compCount++;
        return tab[i] < tab[j];
    }

    public void swap(int i, int j){
        swapCount++;
        int tmp = tab[i];
        tab[i] = tab[j];
        tab[j] = tmp;
    }

    public int length(){
        return tab.length;
    }


}
