package put.apl.Algorithms.Sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import put.apl.Algorithms.Sorting.Data.*;
import put.apl.Algorithms.Sorting.Implementation.*;

import java.util.Arrays;


public class SortTest {

    static SortingData TEST_NUMBERS = new SortingData(new int[]{8, 6, 9, 5, 3, 7, 1, 4, 2});
    static SortingData SORTED_NUMBERS = new SortingData(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

    private SortingData numbers = null;
    private final SelectionSort selectionSort = new SelectionSort();
    private final InsertionSort insertionSort = new InsertionSort();
    private final BinaryInsertionSort binaryInsertionSort = new BinaryInsertionSort();
    private final ShellSort shellSort = new ShellSort();
    private final ShellSortKnuthBase3 shellSortKnuthBase3 = new ShellSortKnuthBase3();
    private final ShellSortKnuthBase2 shellSortKnuthBase2 = new ShellSortKnuthBase2();
    private final MergeSort mergeSort = new MergeSort();
    private final HeapSort heapSort = new HeapSort();
    private final CountingSort countingSort = new CountingSort(9);

    @BeforeEach
    void initAll() {
        numbers = new SortingData(TEST_NUMBERS.getTab().clone());
    }

    @Test
    void selectionSortTest() {
        selectionSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void insertionSortTest() {
        insertionSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void binaryInsertionSortTest() {
        binaryInsertionSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void shellSortTest() {
        shellSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void shellSortKnuthBase3Test() {
        shellSortKnuthBase3.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void shellSortKnuthBase2Test() {
        shellSortKnuthBase2.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void mergeSortTest() {
        mergeSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void heapSortTest() {
        heapSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }

    @Test
    void countingSortTest() {
        countingSort.sort(numbers);
        assertTrue(Arrays.equals(numbers.getTab(), SORTED_NUMBERS.getTab()));
    }
}
