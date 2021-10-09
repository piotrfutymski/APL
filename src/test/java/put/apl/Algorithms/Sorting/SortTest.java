package put.apl.Algorithms.Sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.Algorithms.Sorting.Data.SortingData;
import put.apl.Algorithms.Sorting.Implementation.BubbleSort;
import put.apl.Algorithms.Sorting.Implementation.SelectionSort;

import java.util.Arrays;


public class SortTest {

    static SortingData TEST_NUMBERS = new SortingData(new int[]{ 8,6,9,5,3,7,1,4,2 });
    static SortingData SORTED_NUMBERS = new SortingData(new int[]{ 1,2,3,4,5,6,7,8,9 });

    private SortingData numbers = null;


    @BeforeEach
    void initAll() {
        numbers = new SortingData(TEST_NUMBERS.getTab().clone());
    }

    @Test
    void selectionSortTest(){
        final SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void bubbleSortTest(){
        final BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }
}
