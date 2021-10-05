package put.apl.Algorithms.Sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.Algorithms.Sorting.Data.*;
import put.apl.Algorithms.Sorting.Implementation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class SortTest {

    static SortingData TEST_NUMBERS = new SortingData(new int[]{8, 6, 9, 5, 3, 7, 1, 4, 2});
    static SortingData SORTED_NUMBERS = new SortingData(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

    private SortingData numbers = null;
    private final SelectionSort selectionSort = new SelectionSort();
    private final InsertionSort insertionSort = new InsertionSort();
    private final BinaryInsertionSort binaryInsertionSort = new BinaryInsertionSort();
    private final ShellSort shellSort = new ShellSort();
    private final ShellSortKnuth shellSortKnuth = new ShellSortKnuth();
    private final MergeSort mergeSort = new MergeSort();
    private final HeapSort heapSort = new HeapSort();

    @BeforeEach
    void initAll() {
        numbers = new SortingData(TEST_NUMBERS.getTab().clone());
    }

    @Test
    void selectionSortTest() {
        selectionSort.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void insertionSortTest() {
        insertionSort.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void binaryInsertionSortTest() {
        binaryInsertionSort.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void shellSortTest() {
        shellSort.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void shellSortKnuthBase2Test() {
        Map<String,String> params = Map.of("k", "2");
        shellSortKnuth.setParams(params);
        shellSortKnuth.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void shellSortKnuthBase3Test() {
        Map<String,String> params = Map.of("k", "3");
        shellSortKnuth.setParams(params);
        shellSortKnuth.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void shellSortKnuthException() {
        try{
            shellSortKnuth.sort(numbers);
            fail();
        }catch (IllegalStateException ignored){

        }
    }

    @Test
    void mergeSortTest() {
        mergeSort.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }

    @Test
    void heapSortTest() {
        heapSort.sort(numbers);
        assertArrayEquals(numbers.getTab(), SORTED_NUMBERS.getTab());
    }
}
