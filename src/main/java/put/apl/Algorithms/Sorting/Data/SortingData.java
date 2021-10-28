package put.apl.Algorithms.Sorting.Data;

import lombok.*;

public class SortingData {

    public SortingData(int[] tab){
        this.tab = tab;
    }

    @Getter
    private int[] tab;
    @Getter
    @Setter
    private Long compCount = 0L;
    @Getter
    @Setter
    private Long swapCount = 0L;

    public boolean lessEqual(int i, int j){
        compCount++;
        return tab[i] <= tab[j];
    }

    public boolean less(int i, int j) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
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
